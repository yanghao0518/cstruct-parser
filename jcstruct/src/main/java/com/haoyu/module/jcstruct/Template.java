package com.haoyu.module.jcstruct;

import com.haoyu.module.jcstruct.model.Field;

public interface Template
{
	
	public final String FC_SUFFIX = "Template_FC_";
	
	public final String TC_SUFFIX = "Template_TC_";
	
	public String getId();

	public void setId(String id);

	public String getName();

	public void setName(String name);
	
	public Field[] getFields();

	public void setFields(Field[] fields);

	public Field[] getFirstFields();

	public void setFirstFields(Field[] firstFields);
	
	public Field[] getEndFields();

	public void setEndFields(Field[] endFields);
	
	public void setIsMix(boolean isMix);
	
	public boolean isMix();

	public void addMixInfoFields(String key,Field[] endFields);
	
	public Field[] getMixInfoFields(String key);
	
	public String getMixKey();
	
	public int getByteLength();
	
}
