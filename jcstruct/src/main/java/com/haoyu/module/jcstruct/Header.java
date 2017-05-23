package com.haoyu.module.jcstruct;

import com.haoyu.module.jcstruct.model.Field;

public interface Header
{
	public Field[] getFields();
	
	public int getByteLength();
	
	public String getKey();
}
