package com.haoyu.module.jcstruct.opt;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.model.Field;
import com.haoyu.module.jcstruct.read.JDataInput;
import com.haoyu.module.jcstruct.writer.JDataOutput;

public interface JFieldOpt
{
	public Object read(Field field,JDataInput dataInput,Object ... others) throws IOException;
	
	public void writer(JSONObject obj,Field field,JDataOutput dataOutput,Object ... others) throws IOException;
}
