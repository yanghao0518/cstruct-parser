package com.haoyu.module.jcstruct.resolve;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.writer.JDataOutput;

/**
 * 反解析对属性做处理接口类
 * @author DELL
 *
 */
public interface UnResolvePostProcess
{

	public void beforePostProcess(JSONObject unResolveMessage);
	
	public void afterPostProcess(JSONObject unResolveMessage,JDataOutput dataOutput);
}
