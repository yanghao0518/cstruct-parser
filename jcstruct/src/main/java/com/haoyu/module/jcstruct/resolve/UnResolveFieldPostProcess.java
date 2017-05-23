package com.haoyu.module.jcstruct.resolve;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.model.Field;
import com.haoyu.module.jcstruct.writer.JDataOutput;

/**
 * 对单个属性进行反解析的监听
 * 
 * @author DELL
 *
 */
public interface UnResolveFieldPostProcess
{

	public void postProcess(Field currentField, JSONObject message, JDataOutput dataOutput);

}
