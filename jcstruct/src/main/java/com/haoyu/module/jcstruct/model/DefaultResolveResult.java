package com.haoyu.module.jcstruct.model;


public class DefaultResolveResult<T> implements ResolveResult<T>
{
	private String id;
	
	private T result;
	
	public DefaultResolveResult(String id,T result){
		this.id = id;
		this.result = result;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public T getResult()
	{
		return result;
	}

}
