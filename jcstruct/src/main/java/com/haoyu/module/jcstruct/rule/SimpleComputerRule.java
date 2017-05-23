package com.haoyu.module.jcstruct.rule;

public interface SimpleComputerRule
{
	public Number getIntValue(Number old, Number add);

	public Number getLongValue(Number old, Number add);

	public float getFloatValue(Number old, Number add);

	public double getDoubleValue(Number old, Number add);

}
