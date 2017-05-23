package com.haoyu.module.jcstruct.conn;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.common.SystemConsts;
import com.haoyu.module.jcstruct.dispatch.DispatchCenterService;
import com.haoyu.module.jcstruct.exception.DtuMessageException;
import com.haoyu.module.jcstruct.model.ResolveResult;
import com.haoyu.module.jcstruct.model.ResponseResult;
import com.haoyu.module.jcstruct.resolve.DefaultResolve;
import com.haoyu.module.jcstruct.utils.HexUtils;

public abstract class SocketConnection extends Thread implements Connection
{
	// 能将解析头之后的数据流发送到下一个环节
	protected DefaultResolve defaultResolve;

	protected boolean isStop;

	protected Socket socket;

	protected DispatchCenterService dispatchCenterService;

	protected Logger LOG = Logger.getLogger(this.getClass());

	public abstract PreReadResult preRead() throws IOException;

	public SocketConnection(Socket socket, DispatchCenterService dispatchCenterService, DefaultResolve defaultResolve)
	{
		this.socket = socket;
		this.dispatchCenterService = dispatchCenterService;
		this.defaultResolve = defaultResolve;
	}
	
	protected PreReadResult setPreReadSuccess(byte[] data){
		LOG.info("原始包16进制:" + HexUtils.bytesToHexString(data));
		PreReadResult result = new PreReadResult();
		result.setSkip(false);
		result.setData(data);
		return result;
	}
	
	protected PreReadResult setPreReadSkip(){
		PreReadResult result = new PreReadResult();
		result.setSkip(true);
		return result;
	}

	@Override
	public void startWork()
	{
		this.start();
		LOG.info("客户机:" + getConnectionKey()+",连接成功!");
	}

	@Override
	public void stopWork()
	{
		isStop = true;
	}

	@Override
	public void run()
	{
		while (!isStop) {
			try {
				// 预读
				PreReadResult preReadResult = preRead();
				if(!preReadResult.isSkip()){
					
					ResolveResult<JSONObject> resolveResult = defaultResolve.resolve(preReadResult.getData(), null);
					// 进行数据处理
					ResponseResult result = dispatchCenterService.handle(resolveResult.getId(), resolveResult.getResult());
					// 判断是否需要返回
					if (null != result.getData()) {
						// 返回
						sendMessage(result.getId(), result.getData());
					}
				}
				
			} catch (SocketException e) {
				// 断开连接释放资源
				LOG.error(e.getMessage(),e);
				breakConnection(e);
			} catch (DtuMessageException e) {
				LOG.error(e.getMessage(),e);
				if (e.isNotOnline()) {
					breakConnection(e);
				}
			} catch (EOFException e) {
				LOG.error(e.getMessage(),e);
				//breakConnection(e);
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

	}

	private void breakConnection(Exception e)
	{
		LOG.info(getConnectionKey() + ",已经断开连接!");
		try {
			this.socket.close();
			stopWork();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String getConnectionKey()
	{
		return socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
	}

	public void sendMessage(String messageId, JSONObject message) throws DtuMessageException, IOException
	{
		byte[] sendData = defaultResolve.unresolve(messageId, message);
		if (SystemConsts.isDebug) {
			LOG.info("本次服务器回应网关信息:" + HexUtils.bytesToHexString(sendData));
		}
		this.socket.getOutputStream().write(sendData);
	}

	public boolean checkServerClose()
	{
		try {
			if(isStop){
				return isStop;
			}
			socket.sendUrgentData(0xFF);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
			return false;
		} catch (Exception se) {
			stopWork();
			return true;
		}
	}

}
