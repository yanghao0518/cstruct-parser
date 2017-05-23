package com.haoyu.module.jcstruct.read;

import java.nio.ByteOrder;

import com.haoyu.module.jcstruct.common.SystemConsts;

public final class JDataInputFactoryProxy
{

	private static JDataInputFactory dataInputFactory;

	static {
		if (SystemConsts.order == ByteOrder.BIG_ENDIAN) {
			dataInputFactory = new JDataInputBiggerFactory();
		} else {
			dataInputFactory = new JDataInputLitterFactory();
		}
	}

	public static JDataInput createJDataInput(byte[] data)
	{
		return dataInputFactory.createJDataInput(data);
	}
}
