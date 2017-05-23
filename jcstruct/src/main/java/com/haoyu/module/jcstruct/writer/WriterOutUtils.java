package com.haoyu.module.jcstruct.writer;

import java.io.IOException;

public class WriterOutUtils
{

	public static void writeShort(byte[] list, int start, short v) throws IOException
	{
		writeShort(list, start, v);
	}

	public static void writeFloat(byte[] list, int start, float v) throws IOException
	{
		writeInt(list, start, Float.floatToIntBits(v));
	}

	public static void writeShort(byte[] list, int start, int v) throws IOException
	{
		list[start] = (byte) ((v >>> 8) & 0xFF);
		list[start + 1] = (byte) ((v >>> 0) & 0xFF);
	}

	public static void writeInt(byte[] list, int start, int v) throws IOException
	{
		list[start] = (byte) ((v >>> 24) & 0xFF);
		list[start + 1] = (byte) ((v >>> 16) & 0xFF);
		list[start + 2] = (byte) ((v >>> 8) & 0xFF);
		list[start + 3] = (byte) ((v >>> 0) & 0xFF);
	}

	public static void writeLong(byte[] list, int start, long v) throws IOException
	{
		list[start] = ((byte) (v >>> 56));
		list[start + 1] = ((byte) (v >>> 48));
		list[start + 2] = ((byte) (v >>> 40));
		list[start + 3] = ((byte) (v >>> 32));
		list[start + 4] = ((byte) (v >>> 24));
		list[start + 5] = ((byte) (v >>> 16));
		list[start + 6] = ((byte) (v >>> 8));
		list[start + 7] = ((byte) (v >>> 0));

	}

	public static void writeByte(byte[] list, int start, int v) throws IOException
	{
		list[start] = ((byte) (v & 0xFF));
	}

}
