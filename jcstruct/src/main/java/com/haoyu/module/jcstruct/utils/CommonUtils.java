package com.haoyu.module.jcstruct.utils;

public class CommonUtils
{

	public static byte[] toLH(int n, int index)
	{
		if (index == 4) {
			return toLH(n);
		} else if (index == 2) {
			return toLH2(n);
		} else if (index == 1) {
			return toLH1(n);
		}
		return toLH(n);
	}

	public static byte[] toLH(int n)
	{
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	public static byte[] toLH2(int n)
	{
		byte[] b = new byte[2];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		return b;
	}

	public static byte[] toLH1(int n)
	{
		byte[] b = new byte[1];
		b[0] = (byte) (n & 0xff);
		return b;
	}

	public static int bytes2Integer(byte[] byteVal)
	{
		int result = 0;
		for (int i = 0; i < byteVal.length; i++) {
			int tmpVal = (byteVal[i] << (8 * (3 - i)));
			switch (i)
			{
			case 0:
				tmpVal = tmpVal & 0xFF000000;
				break;
			case 1:
				tmpVal = tmpVal & 0x00FF0000;
				break;
			case 2:
				tmpVal = tmpVal & 0x0000FF00;
				break;
			case 3:
				tmpVal = tmpVal & 0x000000FF;
				break;
			}
			result = result | tmpVal;
		}
		return result;
	}

	// 将C/C++的无符号 DWORD类型转换为java的long型
	public static long getLong(byte buf[], int index)
	{
		int firstByte = (0x000000FF & ((int) buf[index]));
		int secondByte = (0x000000FF & ((int) buf[index + 1]));
		int thirdByte = (0x000000FF & ((int) buf[index + 2]));
		int fourthByte = (0x000000FF & ((int) buf[index + 3]));
		long unsignedLong = ((long) (firstByte | secondByte << 8 | thirdByte << 16 | fourthByte << 24)) & 0xFFFFFFFFL;
		return unsignedLong;
	}

}
