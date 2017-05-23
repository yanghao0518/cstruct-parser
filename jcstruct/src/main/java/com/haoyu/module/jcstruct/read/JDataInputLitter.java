package com.haoyu.module.jcstruct.read;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;


public class JDataInputLitter implements JDataInput
{
	private DataInputStream dataInputStream;
	
	public JDataInputLitter(byte[] data){
		dataInputStream = new DataInputStream(new ByteArrayInputStream(data));
	}

	public final long readUnsignedInt() throws IOException
	{
		byte[] w = new byte[4];
		this.readFully(w, 0, 4);
		return getLong(w, 0);
	}

	public long getLong(byte buf[], int index)
	{
		int firstByte = (0x000000FF & ((int) buf[index]));
		
		int secondByte = (0x000000FF & ((int) buf[index + 1]));
		
		int thirdByte = (0x000000FF & ((int) buf[index + 2]));
		
		int fourthByte = (0x000000FF & ((int) buf[index + 3]));
		
		long unsignedLong = ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
		
		return unsignedLong;
	}

	public final int readCharC(byte w[]) throws IOException
	{
		if (w[0] < 0) {
			return w[0] + 256;
		}
		return (w[0] & 0xff);
	}
	
	public void readBooleanArray(boolean buffer[]) throws IOException
	{
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = readBoolean();
	}

	public void readByteArray(byte buffer[]) throws IOException
	{
		this.readFully(buffer);
	}

	public void readCharArray(char buffer[]) throws IOException
	{
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = readChar();
	}

	public void readShortArray(short buffer[]) throws IOException
	{
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = readShort();
	}

	public void readIntArray(int buffer[]) throws IOException
	{
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = readInt();
	}

	public void readLongArray(long buffer[]) throws IOException
	{
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = readLong();
	}

	public void readFloatArray(float buffer[]) throws IOException
	{
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = readFloat();
	}

	public void readDoubleArray(double buffer[]) throws IOException
	{
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = readDouble();
	}
	
	public void readUnShortedArray(int[] buffer) throws IOException
	{
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = readUnsignedShort();
	}

	@Override
	public void readFully(byte[] b) throws IOException
	{
		dataInputStream.readFully(b);
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException
	{
		dataInputStream.readFully(b, off, len);
	}

	@Override
	public int skipBytes(int n) throws IOException
	{
		return dataInputStream.skipBytes(n);
	}

	@Override
	public boolean readBoolean() throws IOException
	{
		return dataInputStream.readBoolean();
	}

	@Override
	public byte readByte() throws IOException
	{
		return dataInputStream.readByte();
	}

	@Override
	public int readUnsignedByte() throws IOException
	{
		return dataInputStream.readUnsignedByte();
	}

	@Override
	public short readShort() throws IOException
	{
		return dataInputStream.readShort();
	}

	@Override
	public int readUnsignedShort() throws IOException
	{
		return dataInputStream.readUnsignedShort();
	}

	@Override
	public char readChar() throws IOException
	{
		return dataInputStream.readChar();
	}

	@Override
	public int readInt() throws IOException
	{
		return dataInputStream.readInt();
	}

	@Override
	public long readLong() throws IOException
	{
		return dataInputStream.readLong();
	}

	@Override
	public float readFloat() throws IOException
	{
		return dataInputStream.readFloat();
	}

	@Override
	public double readDouble() throws IOException
	{
		return dataInputStream.readDouble();
	}

	@Override
	public String readLine() throws IOException
	{
		return dataInputStream.readLine();
	}

	@Override
	public String readUTF() throws IOException
	{
		return dataInputStream.readUTF();
	}

	@Override
	public int readCharC() throws IOException
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
