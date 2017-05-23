package com.haoyu.module.jcstruct.dispatch;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.model.ResponseResult;

public interface DispatchCenterService
{
	public ResponseResult handle(String id,JSONObject message);
	
}
