package com.haoyu.module.jcstruct.common;

import java.nio.ByteOrder;

public final class SystemConsts
{

	public static boolean isDebug = true;

	public static String DATAPACKAGESIGN = "datapackage";

	public static boolean isStartBCCCheck = false;

	public static final int DEFAULT_PACKAGE_NUM = 0;

	// 大小端
	public static ByteOrder order = ByteOrder.BIG_ENDIAN;

	public static boolean isTest = false;

	// 默认每包波形长度
	public static int transfer_wave_long_version_one = 1024;

	public static int transfer_wave_long = 1004;

	// 最大读取包长度
	public static int max_read = 1024;

	public static int max_read_version_one = 1044;

	public final static String FC_HEADER_DEFAULT_ID = "fc-header";
	public final static String FC_HEADER_DEFAULT_NAME = "fc-header";

	public final static String TC_HEADER_DEFAULT_ID = "tc-header";
	public final static String TC_HEADER_DEFAULT_NAME = "tc-header";

	public final static int port = 19999;

	public final static int maxConnections = 10;

	public static byte[] head = new byte[] { -86, -86 };

	public static byte[] foot = new byte[] { -86, 85 };

}
