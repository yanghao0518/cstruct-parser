package com.haoyu.module.jcstruct.read;

public class JDataInputLitterFactory implements JDataInputFactory
{

	@Override
	public JDataInput createJDataInput(byte[] data)
	{
		return new JDataInputLitter(data);
	}

}
