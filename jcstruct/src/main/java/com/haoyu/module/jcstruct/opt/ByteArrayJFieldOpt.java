package com.haoyu.module.jcstruct.opt;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.model.Field;
import com.haoyu.module.jcstruct.model.SimpleLengthRule;
import com.haoyu.module.jcstruct.read.JDataInput;
import com.haoyu.module.jcstruct.writer.JDataOutput;

public class ByteArrayJFieldOpt extends AbstractJFieldOpt
{

	@Override
	public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
	{

		SimpleLengthRule rule = field.getSimpleLengthRule();

		JSONObject result = (JSONObject) others[0];

		int packageLength = result.getIntValue(rule.getFileName());

		byte[] waveData = new byte[rule.getRule().getIntValue(packageLength, rule.getValue().intValue()).intValue()];

		jDataInput.readByteArray(waveData);

		return waveData;
	}

	@Override
	public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
	{
		Object value = getValue(field, obj);
		dataOutput.writeByteArray((byte[]) value);
	}

}
