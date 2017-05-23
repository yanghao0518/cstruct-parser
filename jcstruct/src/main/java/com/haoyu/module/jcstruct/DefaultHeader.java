package com.haoyu.module.jcstruct;

import com.haoyu.module.jcstruct.model.Field;

public class DefaultHeader implements Header
{

	private String id;
	
	private String name;
	
	private String key;
	
	private Field[] fields;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public Field[] getFields()
	{
		return fields;
	}

	public void setFields(Field[] fields)
	{
		this.fields = fields;
	}

	@Override
	public int getByteLength()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
