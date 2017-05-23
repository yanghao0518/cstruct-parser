package com.haoyu.module.jcstruct.read;

import java.io.DataInput;
import java.io.IOException;

public interface JDataInput extends DataInput
{
	
	public int readCharC() throws IOException;

	public long readUnsignedInt() throws IOException;
	
	public long getLong(byte buf[], int index);
	
	public void readByteArray(byte buffer[]) throws IOException;
	
	public void readShortArray(short buffer[]) throws IOException;
	
	public void readIntArray(int buffer[]) throws IOException;
	
	public void readLongArray(long buffer[]) throws IOException;
	
	public void readFloatArray(float buffer[]) throws IOException;
	
	public void readDoubleArray(double buffer[]) throws IOException;
	
	public void readUnShortedArray(int[] buffer) throws IOException;
	
}
