package com.haoyu.module.jcstruct.rule;

public class SimpleMultiplyRule implements SimpleComputerRule
{

	@Override
	public Number getIntValue(Number old, Number add)
	{
		return (int)(old.doubleValue() * add.doubleValue());
	}

	@Override
	public Number getLongValue(Number old, Number add)
	{
		return (long)(old.doubleValue() * add.doubleValue());
	}

	@Override
	public float getFloatValue(Number old, Number add)
	{
		return old.floatValue() * add.floatValue();
	}

	@Override
	public double getDoubleValue(Number old, Number add)
	{
		return old.doubleValue() * add.doubleValue();
	}

}
