package com.haoyu.module.jcstruct.dispatch;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.handle.HandleMessageService;
import com.haoyu.module.jcstruct.model.ResponseResult;

public class DefaultDispatchCenterServiceImpl extends AbstractDispatchCenterService
{

	@Override
	public ResponseResult handle(String id,JSONObject message)
	{

		HandleMessageService messageService = getHandleMessageService(id);

		return handle(messageService, message);
	}
	
	

}
