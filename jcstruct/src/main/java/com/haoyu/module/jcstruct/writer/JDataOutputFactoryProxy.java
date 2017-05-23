package com.haoyu.module.jcstruct.writer;

import java.nio.ByteOrder;

import com.haoyu.module.jcstruct.common.SystemConsts;

final public class JDataOutputFactoryProxy
{

	private static JDataOutputFactory dataOutputFactory;

	static {
		if (SystemConsts.order == ByteOrder.BIG_ENDIAN) {
			dataOutputFactory = new JDataOutputBiggerFactory();
		} else {
			dataOutputFactory = new JDataOutputLitterFactory();
		}
	}

	public static JDataOutput createJDataOutput()
	{
		return dataOutputFactory.createJDataOutput();
	}

}
