package com.haoyu.module.jcstruct.conn;

public class PreReadResult
{

	public byte[] data;
	
	public boolean isSkip = true;

	public byte[] getData()
	{
		return data;
	}

	public void setData(byte[] data)
	{
		this.data = data;
	}

	public boolean isSkip()
	{
		return isSkip;
	}

	public void setSkip(boolean isSkip)
	{
		this.isSkip = isSkip;
	}
	
	
}
