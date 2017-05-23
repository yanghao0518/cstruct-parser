package com.haoyu.module.jcstruct.model;

import com.alibaba.fastjson.JSONObject;

public class DefaultResponseResult implements ResponseResult
{

	private int code;
	
	private String message;
	
	private JSONObject returnData;
	
	private String id;
	
	private boolean isReturn;
	
	
	public DefaultResponseResult buildCode(int code){
		this.code = code;
		return this;
	}
	
	public DefaultResponseResult buildId(String id){
		this.id = id;
		return this;
	}
	
	public DefaultResponseResult buildMessage(String message){
		this.message = message;
		return this;
	}
	
	public DefaultResponseResult buildReturnData(JSONObject returnData){
		
		this.returnData = returnData;
		
		if(null != returnData){
			isReturn = true;
		}else{
			isReturn = false;
		}
		return this;
	}
	
	
	
	@Override
	public int getCode()
	{
		return this.code;
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}

	@Override
	public JSONObject getData()
	{
		return this.returnData;
	}

	@Override
	public boolean isReptSend()
	{
		return false;
	}

	@Override
	public boolean isReturn()
	{
		return isReturn;
	}

	@Override
	public String getId()
	{
		return id;
	}

}
