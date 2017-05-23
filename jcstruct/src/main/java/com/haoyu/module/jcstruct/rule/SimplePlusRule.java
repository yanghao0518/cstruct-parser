package com.haoyu.module.jcstruct.rule;

public class SimplePlusRule implements SimpleComputerRule
{

	@Override
	public Number getIntValue(Number old, Number add)
	{
		return old.intValue() + add.intValue();
	}

	@Override
	public Number getLongValue(Number old, Number add)
	{
		return old.longValue()  + add.longValue();
	}

	@Override
	public float getFloatValue(Number old, Number add)
	{
		return old.floatValue() + add.floatValue();
	}

	@Override
	public double getDoubleValue(Number old, Number add)
	{
		return old.doubleValue() + add.doubleValue();
	}

}
