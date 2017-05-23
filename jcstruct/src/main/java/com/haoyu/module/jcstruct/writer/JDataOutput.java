package com.haoyu.module.jcstruct.writer;

import java.io.DataOutput;
import java.io.IOException;


public interface JDataOutput extends DataOutput
{
	public byte[] pack();
	
	public void writeUshort(int value) throws IOException;
	
	public void writeUint(long value) throws IOException;
	
	public void writeUbyte(int value) throws IOException;
	
	public void writeBooleanArray(boolean buffer[], int len) throws IOException;
	
	public void writeByteArray(byte buffer[], int len) throws IOException;
	
	public void writeByteArray(byte[] buffer) throws IOException;
	
	public void writeCharArray(char buffer[], int len) throws IOException;
	
	public void writeShortArray(short buffer[], int len) throws IOException;
	
	public void writeIntArray(int buffer[], int len) throws IOException;
	
	public void writeLongArray(long buffer[], int len) throws IOException;
	
	public void writeFloatArray(float buffer[], int len) throws IOException;
	
	public void writeDoubleArray(double buffer[], int len) throws IOException;
	
	public void writeUshortArray(int[] buffer, int len) throws IOException;
	
	public void writeUbyteArray(int[] buffer, int len) throws IOException;
	
}
