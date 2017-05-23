package com.haoyu.module.jcstruct.conn;

import com.haoyu.module.jcstruct.dispatch.DispatchCenterService;
import com.haoyu.module.jcstruct.resolve.DefaultResolve;

public interface DeciseConnection <T>
{
	
	public Connection decise(T t,DispatchCenterService disCenterService,DefaultResolve resovle);

}
