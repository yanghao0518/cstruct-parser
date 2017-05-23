package com.haoyu.module.jcstruct.conn.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.common.SystemConsts;
import com.haoyu.module.jcstruct.conn.DeciseSocketConnection;
import com.haoyu.module.jcstruct.conn.SocketConnection;
import com.haoyu.module.jcstruct.dispatch.DispatchCenterService;
import com.haoyu.module.jcstruct.resolve.DefaultResolve;

public class SocketConnFactoryImpl extends AbstractConnFactory implements SocketConnFactory
{

	private int port = SystemConsts.port;

	private int maxConnections = SystemConsts.maxConnections;

	private int byteOrder;

	private Map<String, SocketConnection> connections;

	private DeciseSocketConnection deciseConnection;

	@Autowired
	private DispatchCenterService dispatchCenterService;

	@Autowired
	private DefaultResolve defaultResolve;

	public int getByteOrder()
	{
		return byteOrder;
	}

	public void setByteOrder(int byteOrder)
	{
		this.byteOrder = byteOrder;
	}

	private boolean isStop;

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public int getMaxConnections()
	{
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections)
	{
		this.maxConnections = maxConnections;
	}

	Logger LOG = Logger.getLogger(this.getClass());

	private void initConnections()
	{
		connections = new ConcurrentHashMap<>();
	}

	@Override
	public void stop()
	{
		isStop = true;
	}

	public void start()
	{
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			ServerSocketThread serverSocketThread = new ServerSocketThread(serverSocket);
			serverSocketThread.start();
			LOG.info("启动监听网关socketserver服务成功！服务信息:"+serverSocket.getLocalSocketAddress().toString());
		} catch (IOException e) {
			LOG.error("启动SocketServer失败:" + e.getMessage(), e);
		}

	}

	private boolean isMaxConnections()
	{
		return connections.size() >= maxConnections;
	}

	public void setDeciseConnection(DeciseSocketConnection deciseConnection)
	{
		this.deciseConnection = deciseConnection;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		// 初始化一些参数
		if (byteOrder == 0) {
			SystemConsts.order = ByteOrder.LITTLE_ENDIAN;
			LOG.info("当前安装小端顺序读取数据！");
		} else {
			SystemConsts.order = ByteOrder.BIG_ENDIAN;
			LOG.info("当前安装大端顺序读取数据！");
		}
		initConnections();
		start();
		//加入网关是否断电的检测
		//new CheckSocketClientThread().start();
	}

	public void addConnection(Socket socket)
	{

		SocketConnection connection = (SocketConnection) deciseConnection.decise(socket, dispatchCenterService, defaultResolve);

		connections.put(connection.getConnectionKey(), connection);

		connection.startWork();
	}

	private final class ServerSocketThread extends Thread
	{

		private ServerSocket serverSocket;

		// 服务器线程的构造方法
		public ServerSocketThread(ServerSocket serverSocket)
		{
			this.serverSocket = serverSocket;
		}

		public void run()
		{
			while (!isStop) {// 不停的等待客户端的链接
				try {
					Socket socket = serverSocket.accept();
					if (isMaxConnections()) {// 如果已达人数上限
						LOG.warn("服务器在线人数已达上限，请稍后尝试连接!");
						socket.close();
						continue;
					}
					addConnection(socket);
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void sendMessage(String messageType, JSONObject message)
	{
		Iterator<Entry<String, SocketConnection>> iterators = connections.entrySet().iterator();
		Entry<String, SocketConnection> entry = null;
		while (iterators.hasNext()) {
			entry = iterators.next();
			try {
				entry.getValue().sendMessage(messageType, message);
			} catch (Exception e) {
				LOG.error("向客户机:" + entry.getKey() + ",发送消息[消息类型=" + messageType + "],失败!" + e.getMessage(), e);
			}
		}
	}

	// 加入检查客户机是否断线机制
	// 实时检查客户端socket是否断掉
	class CheckSocketClientThread extends Thread
	{

		@Override
		public void run()
		{

			while (!isStop) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Iterator<Entry<String, SocketConnection>> iterators = connections.entrySet().iterator();
				Entry<String, SocketConnection> entry = null;
				while (iterators.hasNext()) {
					entry = iterators.next();
					if (entry.getValue().checkServerClose()) {
						LOG.info("客户机:" + entry.getKey()+",与服务器断开!");
						// 删除当前服务
						connections.remove(entry.getKey());
					}
				}
			}

		}

	}
}
