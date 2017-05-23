package com.haoyu.module.jcstruct.common;

/**
 * 基本数据类型转换类
 * 
 * @author DELL
 *
 */
public enum BaseDataType {

	BOOLEAN("boolean", "boolean", 1), BYTE("byte", "byte", 1), CHAR("char", "byte", 1), SHORT("short", "short", 2), USHORT("ushort", "int", 2), INT("int", "int", 4), UINT("unsigendint", "int", 4), LONG("long", "long", 8), FLOAT("float", "float", 4), DOUBLE("double", "double", 8), STRING("string", "string", 0),USHORTARRAY("ushortarray","int[]",0),SHORTARRAY("shortarray","short[]",0),BYTEARRAY("bytearray","byte[]",0);// 字符串的长度是可变的，不能以字节数来决定;
	public String CTYPE;// 对应C++的类型标识
	public String JTYPE;// 对应java的类型标识
	public int BYTES;// 占用的字节数

	private BaseDataType(String CTYPE, String JTYPE, int BYTES)
	{
		this.CTYPE = CTYPE;
		this.JTYPE = JTYPE;
		this.BYTES = BYTES;
	}

}
