package com.haoyu.module.jcstruct.read;

import java.nio.ByteOrder;

import com.haoyu.module.jcstruct.common.SystemConsts;

public final class ReadInputProxyUtil
{

	private static ReadInput readInput;

	static {
		if (SystemConsts.order == ByteOrder.BIG_ENDIAN) {
			readInput = new ReadInputBigger();
		} else {
			readInput = new ReadInputLitter();
		}
	}

	public static short readShort(byte[] w)
	{
		return readInput.readShort(w);
	}

	public static int readUnsignedShort(byte[] w)
	{
		return readInput.readUnsignedShort(w);
	}

	public static char readChar(byte[] w)
	{
		return readInput.readChar(w);
	}

	public static int readInt(byte[] w)
	{
		return readInput.readInt(w);
	}

	public static long readUnsignedInt(byte[] w)
	{
		return readInput.readUnsignedInt(w);
	}

	public static long readLong(byte[] w)
	{
		return readInput.readLong(w);
	}

	public static float readFloat(byte[] w)
	{
		return readInput.readFloat(w);
	}

	public static double readDouble(byte[] w)
	{
		return readInput.readDouble(w);
	}

	public static boolean readBoolean(byte[] w)
	{
		return readInput.readBoolean(w);
	}

	public static byte readUnsignedByte(byte[] w)
	{
		return readInput.readUnsignedByte(w);
	}

	public static int readCharC(byte[] w)
	{
		return readInput.readCharC(w);
	}

	public static int readByte(byte[] w)
	{
		return readInput.readByte(w);
	}

	public static int[] readShortArray(byte[] w)
	{
		return readInput.readShortArray(w);
	}

	public static int[] readUShortArray(byte[] w)
	{
		return readInput.readUShortArray(w);
	}

	public static String readUTF(byte[] w)
	{
		return readInput.readUTF(w);
	}

	public static String readUTF(byte[] w, String encoding)
	{
		return readInput.readUTF(w, encoding);
	}

}
