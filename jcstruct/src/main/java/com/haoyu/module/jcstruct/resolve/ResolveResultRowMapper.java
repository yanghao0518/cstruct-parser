package com.haoyu.module.jcstruct.resolve;

import com.alibaba.fastjson.JSONObject;

public interface ResolveResultRowMapper<T extends Object>
{
	public T mapRow(JSONObject row);
}
