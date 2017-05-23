package com.haoyu.module.jcstruct.model;

import com.haoyu.module.jcstruct.common.BaseDataType;

public class FieldAttrFactory
{

	private static ComputerFieldRealValue computerCommonFieldRealValue;

	private static ComputerFieldRealValue computerFieldRealIntValue;

	private static ComputerFieldRealValue computerFieldRealFloatValue;

	private static ComputerFieldRealValue computerFieldRealLongValue;

	private static ComputerFieldRealValue computerFieldRealDoubleValue;

	static {
		computerCommonFieldRealValue = new ComputerFieldRealValue()
		{

			@Override
			public Number getRealValue(Number old, SimpleValueRule simpleRule)
			{
				return old;
			}
		};

		computerFieldRealIntValue = new ComputerFieldRealValue()
		{
			@Override
			public Number getRealValue(Number old, SimpleValueRule simpleRule)
			{
				return simpleRule.getRule().getIntValue(old, simpleRule.getValue());
			}
		};

		computerFieldRealLongValue = new ComputerFieldRealValue()
		{
			@Override
			public Number getRealValue(Number old, SimpleValueRule simpleRule)
			{
				return simpleRule.getRule().getLongValue(old, simpleRule.getValue());
			}
		};

		computerFieldRealFloatValue = new ComputerFieldRealValue()
		{
			@Override
			public Number getRealValue(Number old, SimpleValueRule simpleRule)
			{
				return simpleRule.getRule().getFloatValue(old, simpleRule.getValue());
			}
		};

		computerFieldRealDoubleValue = new ComputerFieldRealValue()
		{
			@Override
			public Number getRealValue(Number old, SimpleValueRule simpleRule)
			{
				return simpleRule.getRule().getDoubleValue(old, simpleRule.getValue());
			}
		};

	}

	public static ComputerFieldRealValue getCommonComputerFieldRealValue()
	{
		return computerCommonFieldRealValue;
	}

	public static ComputerFieldRealValue getComputerFieldRealValue(BaseDataType type)
	{
		switch (type)
		{
		case INT:
			return computerFieldRealIntValue;
		case UINT:
			return computerFieldRealLongValue;
		case SHORT:
			return computerFieldRealIntValue;
		case USHORT:
			return computerFieldRealIntValue;
		case LONG:
			return computerFieldRealLongValue;
		case FLOAT:
			return computerFieldRealFloatValue;
		case DOUBLE:
			return computerFieldRealDoubleValue;
		default:
			throw new IllegalArgumentException("不支持的按规则计算值类型->" + type.JTYPE);
		}

	}
}
