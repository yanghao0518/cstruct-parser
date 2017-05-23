package com.haoyu.module.jcstruct.writer;

public class JDataOutputBiggerFactory implements JDataOutputFactory
{

	@Override
	public JDataOutput createJDataOutput()
	{
		return new JDataOutputBigger();
	}

}
