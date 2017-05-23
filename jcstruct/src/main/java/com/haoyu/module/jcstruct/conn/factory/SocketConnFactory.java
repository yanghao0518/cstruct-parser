package com.haoyu.module.jcstruct.conn.factory;

import com.alibaba.fastjson.JSONObject;

public interface SocketConnFactory
{
	public void sendMessage(String id,JSONObject message);
}
