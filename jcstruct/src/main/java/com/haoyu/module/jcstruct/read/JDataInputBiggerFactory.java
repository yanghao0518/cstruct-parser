package com.haoyu.module.jcstruct.read;

public class JDataInputBiggerFactory implements JDataInputFactory
{

	@Override
	public JDataInput createJDataInput(byte[] data)
	{
		return new JDataInputBigger(data);
	}

}
