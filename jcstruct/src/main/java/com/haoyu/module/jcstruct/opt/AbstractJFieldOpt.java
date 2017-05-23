package com.haoyu.module.jcstruct.opt;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.model.Field;

abstract public class AbstractJFieldOpt implements JFieldOpt
{

	protected Object getValue(Field field, JSONObject content)
	{
		String fieldName = field.getName();
		Object value = content.get(fieldName);
		value = (null == value) ? field.getDefaultValue() : value;
		Assert.notNull(value, "the protocol 's field[ " + fieldName + "] not allow null,please set default value or set current message value");
		return value;
	}

	protected Number getNumberValue(Field field, JSONObject content)
	{
		String fieldName = field.getName();
		Number value = (Number) content.get(fieldName);
		value = (null == value) ? (Number) field.getDefaultValue() : value;
		Assert.notNull(value, "the protocol 's field[ " + fieldName + "] not allow null,please set default value or set current message value");
		return field.getRealValue(value);
	}
	
	protected Number getNumberValueNoRule(Field field, JSONObject content)
	{
		String fieldName = field.getName();
		Number value = (Number) content.get(fieldName);
		value = (null == value) ? (Number) field.getDefaultValue() : value;
		Assert.notNull(value, "the protocol 's field[ " + fieldName + "] not allow null,please set default value or set current message value");
		return value;
	}

}
