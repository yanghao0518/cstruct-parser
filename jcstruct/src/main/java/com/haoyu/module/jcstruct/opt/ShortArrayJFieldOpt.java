package com.haoyu.module.jcstruct.opt;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.model.Field;
import com.haoyu.module.jcstruct.read.JDataInput;
import com.haoyu.module.jcstruct.writer.JDataOutput;

public class ShortArrayJFieldOpt extends AbstractJFieldOpt
{

	@Override
	public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
	{
		System.out.println("ShortArray解析开始!长度：" + others[0]);
		return null;
	}

	@Override
	public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
	{
		Object value = getValue(field, obj);
		dataOutput.writeShortArray((short[]) value, -1);
	}

}
