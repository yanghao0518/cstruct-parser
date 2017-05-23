package com.haoyu.module.jcstruct;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.haoyu.module.jcstruct.model.Field;

public class DefaultTemplate implements Template, InitializingBean
{

	private String id;

	private String name;

	private Field[] fields;// 字段集合

	private Field[] firstFields;// 开始公共集合

	private Field[] endFields;// 结束公共集合

	private boolean isMix;// 是否为混合协议

	private Map<String, Field[]> mixInfo;

	private String mixKey;

	public String getMixKey()
	{
		return mixKey;
	}

	public void setMixKey(String mixKey)
	{
		this.mixKey = mixKey;
	}

	public Field[] getFirstFields()
	{
		return firstFields;
	}

	public void setFirstFields(Field[] firstFields)
	{
		this.firstFields = firstFields;
	}

	public Field[] getEndFields()
	{
		return endFields;
	}

	public void setEndFields(Field[] endFields)
	{
		this.endFields = endFields;
	}

	public boolean isMix()
	{
		return isMix;
	}

	public void setIsMix(boolean isMix)
	{
		if (isMix && null == mixInfo) {
			mixInfo = new HashMap<String, Field[]>();
		}
		this.isMix = isMix;
	}

	public Map<String, Field[]> getMixInfo()
	{
		return mixInfo;
	}

	public void setMixInfo(Map<String, Field[]> mixInfo)
	{
		this.mixInfo = mixInfo;
	}

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

	public Field[] getFields()
	{
		return fields;
	}

	public void setFields(Field[] fields)
	{
		this.fields = fields;
	}

	@Override
	public void addMixInfoFields(String key, Field[] fields)
	{
		mixInfo.put(key, fields);
	}

	@Override
	public Field[] getMixInfoFields(String key)
	{
		return mixInfo.get(key);
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{

		if(null == firstFields){
			firstFields = new Field[]{};
		}
		if(null == endFields){
			endFields = new Field[]{};
		}
		
	}

	@Override
	public String toString()
	{
		return "DefaultTemplate [id=" + id + ", name=" + name + ", fields=" + Arrays.toString(fields) + ", firstFields=" + Arrays.toString(firstFields) + ", endFields=" + Arrays.toString(endFields) + ", isMix=" + isMix + ", mixInfo=" + mixInfo + ", mixKey=" + mixKey + "]";
	}

	@Override
	public int getByteLength()
	{
		return 0;
	}

	
	
}
