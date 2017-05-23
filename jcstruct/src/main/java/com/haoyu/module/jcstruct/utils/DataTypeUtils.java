package com.haoyu.module.jcstruct.utils;

import java.util.HashMap;

import com.haoyu.module.jcstruct.common.BaseDataType;

public class DataTypeUtils
{

	private static HashMap<String, BaseDataType> dataTypes = new HashMap<String, BaseDataType>();

	static {
		for (BaseDataType d : BaseDataType.values()) {
			dataTypes.put(d.CTYPE, d);
		}
	}

	public static final BaseDataType getDataType(String ctype)
	{
		BaseDataType d = dataTypes.get(ctype);
		return d;
	}

	public static Object getBaseDateValue(BaseDataType type, String value)
	{
		switch (type)
		{
		case BOOLEAN:
			return Boolean.valueOf(value);
		case BYTE:
			int radix = getRadix(value);
			value = value.replace("0X", "").replace("0x", "");
			return Byte.parseByte(value, radix);
		case CHAR:
			radix = getRadix(value);
			value = value.replace("0X", "").replace("0x", "");
			return Byte.parseByte(value, radix);
		case SHORT:
			radix = getRadix(value);
			value = value.replace("0X", "").replace("0x", "");
			if (NumberValidationUtils.isWholeNumber(value)) {
				return Integer.parseInt(value, radix);
			} else {
				return Float.parseFloat(value);
			}
		case USHORT:

			radix = getRadix(value);
			value = value.replace("0X", "").replace("0x", "");
			if (NumberValidationUtils.isWholeNumber(value)) {
				return Integer.parseInt(value, radix);
			} else {
				return Float.parseFloat(value);
			}

		case INT:

			radix = getRadix(value);
			value = value.replace("0X", "").replace("0x", "");
			if (NumberValidationUtils.isWholeNumber(value)) {
				return Integer.parseInt(value, radix);
			} else {
				return Float.parseFloat(value);
			}

		case UINT:
			radix = getRadix(value);
			value = value.replace("0X", "").replace("0x", "");
			if (NumberValidationUtils.isWholeNumber(value)) {
				return Long.parseLong(value, radix);
			} else {
				return Float.parseFloat(value);
			}

		case LONG:
			
			radix = getRadix(value);
			value = value.replace("0X", "").replace("0x", "");
			if (NumberValidationUtils.isWholeNumber(value)) {
				return Long.parseLong(value, radix);
			} else {
				return Float.parseFloat(value);
			}

		case FLOAT:
			return Float.parseFloat(value);
		case DOUBLE:
			return Double.parseDouble(value);
		default:
			return value;
		}
	}

	public static int getRadix(String value)
	{
		if (value.toUpperCase().startsWith("0X")) {
			return 16;
		}

		if (value.toUpperCase().startsWith("0")) {
			return 8;
		}
		return 10;

	}

}
