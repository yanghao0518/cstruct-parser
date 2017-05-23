package com.haoyu.module.jcstruct.writer;

public class JDataOutputLitterFactory implements JDataOutputFactory
{

	@Override
	public JDataOutput createJDataOutput()
	{
		return new JDataOutputLitter();
	}

}
