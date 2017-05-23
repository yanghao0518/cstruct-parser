package com.haoyu.module.jcstruct.resolve;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.common.SystemConsts;
import com.haoyu.module.jcstruct.exception.DtuMessageException;
import com.haoyu.module.jcstruct.model.Field;
import com.haoyu.module.jcstruct.model.SimpleLengthRule;
import com.haoyu.module.jcstruct.read.JDataInput;
import com.haoyu.module.jcstruct.read.JDataInputFactoryProxy;

public final class ResolveUtil 
{

	public static JDataInput createJDataInput(byte[] data)
	{
		return JDataInputFactoryProxy.createJDataInput(data);
	}

	public static JSONObject resolve(byte[] source, Field[] fields) throws DtuMessageException, IOException
	{
		// 解析
		int endIndex = fields.length;
		JSONObject headJson = new JSONObject();
		JDataInput jdataInput = createJDataInput(source);
		for (int i = 0; i < endIndex; i++) {
			readStream(jdataInput, fields[i], headJson);
		}
		return headJson;
	}
	
	public static JSONObject resolve(JDataInput dataInput, Field[] fields) throws DtuMessageException, IOException
	{
		// 解析
		int endIndex = fields.length;
		JSONObject headJson = new JSONObject();
		for (int i = 0; i < endIndex; i++) {
			readStream(dataInput, fields[i], headJson);
		}
		return headJson;
	}

	private static void readStream(JDataInput dataInput, Field field, JSONObject headJson) throws DtuMessageException, IOException
	{
		switch (field.getType())
		{
		case BOOLEAN:
			headJson.put(field.getName(), dataInput.readBoolean());
			break;
		case BYTE:
			headJson.put(field.getName(), dataInput.readByte());
			break;
		case CHAR:
			headJson.put(field.getName(), dataInput.readCharC());
			break;
		case SHORT:
			headJson.put(field.getName(), dataInput.readShort());
			break;
		case USHORT:
			headJson.put(field.getName(), dataInput.readUnsignedShort());
			break;
		case INT:
			headJson.put(field.getName(), dataInput.readInt());
			break;
		case UINT:
			headJson.put(field.getName(), dataInput.readUnsignedInt());
			break;
		case FLOAT:
			headJson.put(field.getName(), dataInput.readFloat());
			break;
		case DOUBLE:
			headJson.put(field.getName(), dataInput.readDouble());
			break;
		case STRING:
			headJson.put(field.getName(), dataInput.readUTF());
			break;
		case BYTEARRAY:
			// 注意此次可能是波形，暂时写死判断在此处
			int waveLength = SystemConsts.transfer_wave_long;
			
			if(null != field.getSimpleLengthRule()){
				SimpleLengthRule rule = field.getSimpleLengthRule();
				
				int packageLength = headJson.getIntValue(rule.getFileName());
				
				waveLength = rule.getRule().getIntValue(packageLength, rule.getValue().intValue()).intValue();
			}
			
			byte[] waveData = new byte[waveLength];
			dataInput.readByteArray(waveData);
			headJson.put(field.getName(), waveData);
			return;
		default:
			throw new DtuMessageException(DtuMessageException.UNKNOWN_EXCEPTION, "not undefined jtype:" + field.getType().JTYPE);

		}
	}

}
