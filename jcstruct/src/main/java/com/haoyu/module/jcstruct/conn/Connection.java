package com.haoyu.module.jcstruct.conn;

public interface Connection
{
	public void startWork();
	
	public void stopWork();
	
	public String getConnectionKey();
}
