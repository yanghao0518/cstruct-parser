package com.haoyu.module.jcstruct.resolve;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.exception.DtuMessageException;

public interface Resolve
{
	
	public byte[] unresolve(String id,JSONObject record) throws DtuMessageException, IOException;
}
