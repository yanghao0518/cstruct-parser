package com.haoyu.module.jcstruct.check;

public class DefaultCJavaCheckImpl implements CJavaCheck
{

	private String name;
	private String id;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public void check()
	{

		System.err.println("this name:"+name+",this id:"+id);
	}

}
