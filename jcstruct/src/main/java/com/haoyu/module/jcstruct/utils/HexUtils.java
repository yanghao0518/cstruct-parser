package com.haoyu.module.jcstruct.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class HexUtils
{

	public static String getHexString(Integer i)
	{
		return "0X" + Integer.toHexString(i);
	}

	public static String getHexStringL(Long i)
	{
		return "0X" + Long.toHexString(i);
	}

	public static Integer getIntFromHex(String str)
	{
		if (str.startsWith("0X") || str.startsWith("0x")) {
			str = str.substring(2);
			return Integer.parseInt(str, 16);
		}
		return Integer.parseInt(str);
	}
	
	public static Long getLongFromHex(String str)
	{
		if (str.startsWith("0X") || str.startsWith("0x")) {
			str = str.substring(2);
			return Long.parseLong(str, 16);
		}
		return Long.parseLong(str);
	}

	public static String unStringFromHex(String str)
	{
		if (str.startsWith("0X") || str.startsWith("0x")) {
			str = str.substring(2);
			return str;
		}
		return str;
	}

	public static String toStringFromHex(String str)
	{
		if (null != str && !(str.startsWith("0X") || str.startsWith("0x"))) {
			return "0X" + str;
		}
		return str;
	}

	/**
	 * 把byte转化成2进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String getBinaryStrFromByte(byte b)
	{
		String result = "";
		byte a = b;
		;
		for (int i = 0; i < 8; i++) {
			byte c = a;
			a = (byte) (a >> 1);// 每移一位如同将10进制数除以2并去掉余数。
			a = (byte) (a << 1);
			if (a == c) {
				result = "0" + result;
			} else {
				result = "1" + result;
			}
			a = (byte) (a >> 1);
		}
		return result;
	}

	public static String bytesToHexString(byte[] src)
	{
		
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {

			int v = src[i] & 0xFF;

			String hv = Integer.toHexString(v);

			if (hv.length() < 2) {

				stringBuilder.append(0);

			}

			stringBuilder.append(hv);

		}
		String s = stringBuilder.toString();

		StringBuilder s2 = new StringBuilder("");

		for (int i = 0; i < s.length(); i++) {
			if (i != 0 && i % 2 == 0) {
				s2.append(" ");
			}
			s2.append(s.charAt(i));

		}

		stringBuilder = null;

		return s2.toString();
	}
	

}
