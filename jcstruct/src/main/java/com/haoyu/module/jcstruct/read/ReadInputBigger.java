package com.haoyu.module.jcstruct.read;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.haoyu.module.jcstruct.exception.DtuMessageException;

/**
 * 大端
 * 
 * @author DELL
 *
 */
public class ReadInputBigger implements ReadInput
{

	private Logger LOG = Logger.getLogger(this.getClass());

	public final short readShort(byte[] w)
	{
		return (short) ((w[0] & 0xff) << 8 | (w[1] & 0xff));
	}

	public final int readUnsignedShort(byte[] w)
	{
		return ((w[0] & 0xff) << 8 | (w[1] & 0xff));
	}

	public final char readChar(byte[] w)
	{
		return (char) ((w[0] & 0xff) << 8 | (w[1] & 0xff));
	}

	public final int readInt(byte[] w)
	{
		return (w[0] & 0xff) << 24 | (w[1] & 0xff) << 16 | (w[2] & 0xff) << 8 | (w[3] & 0xff);
	}

	public final long readUnsignedInt(byte[] w)
	{
		return getLong(w, 0);
	}

	public final long readLong(byte[] w)
	{
		return (long) (w[0] & 0xff) << 56
				| /* long cast needed or shift done modulo 32 */
				(long) (w[1] & 0xff) << 48 | (long) (w[2] & 0xff) << 40 | (long) (w[3] & 0xff) << 32 | (long) (w[4] & 0xff) << 24 | (long) (w[5] & 0xff) << 16 | (long) (w[6] & 0xff) << 8 | (long) (w[7] & 0xff);
	}

	public final float readFloat(byte[] w)
	{
		return Float.intBitsToFloat(readInt(w));
	}

	/**
	 * like DataInputStream.readDouble except little endian.
	 */
	public final double readDouble(byte[] w)
	{
		return Double.longBitsToDouble(readLong(w));
	}

	public final boolean readBoolean(byte[] w)
	{
		if (w[0] < 0)
			throw new DtuMessageException(DtuMessageException.INVALID_BYTE_ARRAY_EXCEPTION);
		return w[0] != 0;
	}

	public final byte readUnsignedByte(byte[] w)
	{
		if (w[0] < 0)
			throw new DtuMessageException(DtuMessageException.INVALID_BYTE_ARRAY_EXCEPTION);
		return (byte) w[0];
	}

	public final int readCharC(byte[] w)
	{
		if (w[0] < 0) {
			return w[0] + 256;
		}
		return (w[0] & 0xff);
	}

	public final int readByte(byte[] w)
	{
		return (byte) w[0];
	}

	public final void readShortArray(byte[] w, short[] datas)
	{

		int size = datas.length;
		byte[] org = null;
		for (int i = 0; i < size; i++) {
			org = new byte[2];
			org[0] = w[2 * i];
			org[1] = w[2 * i + 1];
			datas[i] = readShort(org);
		}
	}

	public final void readUShortArray(byte[] w, int[] datas)
	{

		int size = datas.length;
		byte[] org = null;
		for (int i = 0; i < size; i++) {
			org = new byte[2];
			org[0] = w[2 * i];
			org[1] = w[2 * i + 1];
			datas[i] = readUnsignedShort(org);
		}
		// LOG.info("读取波形成功!");
	}

	public final String readUTF(byte[] w)
	{
		return new String(w);
	}

	public final String readUTF(byte[] w, String encoding)
	{
		try {
			return new String(w, encoding);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

	public long getLong(byte buf[], int index)
	{
		int firstByte = (0x000000FF & ((int) buf[index]));
		int secondByte = (0x000000FF & ((int) buf[index + 1]));
		int thirdByte = (0x000000FF & ((int) buf[index + 2]));
		int fourthByte = (0x000000FF & ((int) buf[index + 3]));
		long unsignedLong = ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
		return unsignedLong;
	}

	@Override
	public int[] readShortArray(byte[] w)
	{
		int size = w.length / 2;
		int[] datas = new int[size];
		byte[] org = null;
		for (int i = 0; i < size; i++) {
			org = new byte[2];
			org[0] = w[2 * i];
			org[1] = w[2 * i + 1];
			datas[i] = readShort(org);
		}
		return datas;
	}

	@Override
	public int[] readUShortArray(byte[] w)
	{
		int size = w.length / 2;
		int[] datas = new int[size];
		byte[] org = null;
		for (int i = 0; i < size; i++) {
			org = new byte[2];
			org[0] = w[2 * i];
			org[1] = w[2 * i + 1];
			datas[i] = readUnsignedShort(org);
		}
		return datas;
	}

}
