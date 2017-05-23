package com.haoyu.module.jcstruct.model;

import com.alibaba.fastjson.JSONObject;

public interface ResponseResult {

	public int getCode();
	
	public String getId();
	
	public String getMessage();
	
	public JSONObject getData();
	
	public boolean isReptSend();
	
	public boolean isReturn();
	
}
