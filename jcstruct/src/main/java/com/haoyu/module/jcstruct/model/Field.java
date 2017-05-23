package com.haoyu.module.jcstruct.model;

import com.haoyu.module.jcstruct.common.BaseDataType;

public class Field
{
	private String name;// 字段名称

	private BaseDataType type;// 数据类型 需要包括该数据项具体占有多少个字节数

	private Object defaultValue;

	private int variableLength;// 可变长度

	private SimpleValueRule simpleValueRule;

	private SimpleLengthRule simpleLengthRule;

	private ComputerFieldRealValue computerFieldRealValue = FieldAttrFactory.getCommonComputerFieldRealValue();

	public int getVariableLength()
	{
		return variableLength;
	}

	public void setVariableLength(int variableLength)
	{
		this.variableLength = variableLength;
	}

	public Object getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public Field(String name, BaseDataType type)
	{
		this.name = name;
		this.type = type;
	}

	public Field()
	{
	}

	public Field(String name, BaseDataType type, Object defaultValue)
	{
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public BaseDataType getType()
	{
		return type;
	}

	public void setType(BaseDataType type)
	{
		this.type = type;
	}

	public SimpleValueRule getSimpleValueRule()
	{
		return simpleValueRule;
	}

	public void setSimpleValueRule(SimpleValueRule simpleValueRule)
	{
		this.simpleValueRule = simpleValueRule;
		if (null != simpleValueRule) {
			computerFieldRealValue = FieldAttrFactory.getComputerFieldRealValue(type);
		}
	}

	public SimpleLengthRule getSimpleLengthRule()
	{
		return simpleLengthRule;
	}

	public void setSimpleLengthRule(SimpleLengthRule simpleLengthRule)
	{
		this.simpleLengthRule = simpleLengthRule;
	}

	public Number getRealValue(Number old)
	{
		return computerFieldRealValue.getRealValue(old, simpleValueRule);
	}

}
