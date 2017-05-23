package com.haoyu.module.jcstruct.conn;

import java.net.Socket;

import com.haoyu.module.jcstruct.dispatch.DispatchCenterService;
import com.haoyu.module.jcstruct.resolve.DefaultResolve;

public class DefaultDeciseSocketConnection extends DeciseSocketConnection
{

	@Override
	public Connection decise(Socket t, DispatchCenterService disCenterService, DefaultResolve resovle)
	{
		Connection connection = new SocketConnectionImpl(t, disCenterService, resovle);
		return connection;
	}

}
