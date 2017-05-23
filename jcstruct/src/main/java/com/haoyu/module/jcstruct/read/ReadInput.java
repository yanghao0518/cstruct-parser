package com.haoyu.module.jcstruct.read;



public interface ReadInput
{

	public short readShort(byte[] w);

	public int readUnsignedShort(byte[] w);

	public char readChar(byte[] w);

	public int readInt(byte[] w);

	public long readUnsignedInt(byte[] w);

	public long readLong(byte[] w);

	public float readFloat(byte[] w);

	public double readDouble(byte[] w);

	public boolean readBoolean(byte[] w);

	public byte readUnsignedByte(byte[] w);

	public int readCharC(byte[] w);

	public int readByte(byte[] w);

	public int[] readShortArray(byte[] w);

	public int[] readUShortArray(byte[] w);

	public String readUTF(byte[] w);

	public String readUTF(byte[] w, String encoding);

}
