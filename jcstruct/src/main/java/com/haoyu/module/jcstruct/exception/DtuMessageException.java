package com.haoyu.module.jcstruct.exception;

import java.io.Serializable;

/**
 * 
 * @author hao.yang
 * @export
 */
public final class DtuMessageException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -1616390342330023360L;

	public static final int UNKNOWN_EXCEPTION = 99;

	public static final int CONNECTION_EXCEPTION = 1;

	public static final int SQL_EXCEPTION = 2;

	public static final int REDIS_EXCEPTION = 3;

	public static final int FORBIDDEN_EXCEPTION = 4;

	public static final int SERIALIZATION_EXCEPTION = 5;

	public static final int NOTFOUND_SOURCEID = 6;

	public static final int MQ_EXCEPTION = 7;

	public static final int NOTFOUND_PARSETYPE_ID = 8;
	
	public static final int CLIENT_NOT_ONLINE = 9;
	
	public static final int BBC_CHECK_EXCEPTION = 10;
	
	public static final int INVALID_BYTE_ARRAY_EXCEPTION = 11;

	private int code; // DtuMessageException不能有子类，异常类型用ErrorCode表示，以便保持兼容。

	public DtuMessageException() {
		super();
	}

	public DtuMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public DtuMessageException(String message) {
		super(message);
	}

	public DtuMessageException(Throwable cause) {
		super(cause);
	}

	public DtuMessageException(int code) {
		super();
		this.code = code;
	}

	public DtuMessageException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public DtuMessageException(int code, String message) {
		super(message);
		this.code = code;
	}

	public DtuMessageException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public boolean isSql() {
		return code == SQL_EXCEPTION;
	}

	public boolean isForbidded() {
		return code == FORBIDDEN_EXCEPTION;
	}

	public boolean isConnection() {
		return code == CONNECTION_EXCEPTION;
	}

	public boolean isRedis() {
		return code == REDIS_EXCEPTION;
	}

	public boolean isSerialization() {
		return code == SERIALIZATION_EXCEPTION;
	}

	public boolean isMq() {
		return code == MQ_EXCEPTION;
	}
	
	public boolean isNotOnline(){
		return code == CLIENT_NOT_ONLINE;
	}
	
	public boolean isBBCCheck(){
		return code == BBC_CHECK_EXCEPTION;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return null;
	}
}