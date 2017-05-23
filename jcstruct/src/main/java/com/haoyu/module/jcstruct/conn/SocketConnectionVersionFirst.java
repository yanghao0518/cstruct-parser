package com.haoyu.module.jcstruct.conn;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import org.apache.commons.lang3.ArrayUtils;

import com.haoyu.module.jcstruct.common.SystemConsts;
import com.haoyu.module.jcstruct.dispatch.DispatchCenterService;
import com.haoyu.module.jcstruct.resolve.DefaultResolve;
import com.haoyu.module.jcstruct.utils.HexUtils;

public class SocketConnectionVersionFirst extends SocketConnection
{

	private byte[] last = null;

	private boolean isStartPackage(byte[] packages)
	{
		// 取出前两位
		byte b0 = packages[0], b1 = packages[1];
		return (b0 == SystemConsts.head[0] && b1 == SystemConsts.head[1]);
	}

	private boolean isEndPackage(byte[] packages)
	{
		// 取出前两位
		byte b0 = packages[packages.length - 2], b1 = packages[packages.length - 1];
		return (b0 == SystemConsts.foot[0] && b1 == SystemConsts.foot[1]);
	}

	public SocketConnectionVersionFirst(Socket socket, DispatchCenterService dispatchCenterService, DefaultResolve defaultResolve)
	{
		super(socket, dispatchCenterService, defaultResolve);
	}

	public byte[] read() throws IOException
	{

		byte[] data = new byte[SystemConsts.max_read_version_one];

		int count = socket.getInputStream().read(data);

		if (count == -1) {
			throw new SocketException();
		}

		LOG.info("本次读取数据总和:" + count);

		if (count == SystemConsts.max_read_version_one) {

			LOG.info("本次读取数据16进制:" + HexUtils.bytesToHexString(data));

			return data;
		}

		byte[] tmp = new byte[count];

		System.arraycopy(data, 0, tmp, 0, count);

		return tmp;
	}

	@Override
	public boolean checkServerClose()
	{
		try {
			socket.sendUrgentData(0xFF);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
			return false;
		} catch (Exception se) {
			return true;
		}
	}

	@Override
	public PreReadResult preRead() throws IOException
	{

		byte[] data = read();

		boolean isStartPackage = isStartPackage(data);

		boolean isEndPackage = isEndPackage(data);

		if (isStartPackage) {

			if (isEndPackage) {
				// 完整处理
				return setPreReadSuccess(data);
			} else {
				last = data;
			}

		} else {

			if (last != null) {
				last = ArrayUtils.addAll(last, data);
			}

			// 如果不是一个结束包 不进行解析
			if (isEndPackage) {
				PreReadResult result = setPreReadSuccess(last);
				last = null;
				return result;
			}

		}

		return setPreReadSkip();
	}

}
