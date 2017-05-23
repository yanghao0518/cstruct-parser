package com.haoyu.module.jcstruct.resolve;

import java.io.IOException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.Template;
import com.haoyu.module.jcstruct.exception.DtuMessageException;
import com.haoyu.module.jcstruct.model.Field;
import com.haoyu.module.jcstruct.model.ResolveResult;
import com.haoyu.module.jcstruct.read.JDataInput;
import com.haoyu.module.jcstruct.writer.JDataOutput;

@Service
public class DefaultResolveImpl extends AbstractResolve implements DefaultResolve
{

	@Override
	public byte[] unresolve(String id, JSONObject record) throws DtuMessageException, IOException
	{
		// 解析前过滤
		beforeUnresolve(record);

		// 解析出content
		Template template = getTCTemplate(id);

		JDataOutput dataOutput = createJDataOutput();

		// 遍历头
		Field[] fields = getTcHeaderFields();

		writerFields(fields, record, dataOutput);

		// 解析数据
		fields = template.getFields();

		writerFields(fields, record, dataOutput);

		// 解析后处理
		afterUnresolve(record, dataOutput);

		return dataOutput.pack();
	}

	@Override
	public <T> ResolveResult<T> resolve(byte[] data, ResolveResultRowMapper<T> rowMapper) throws DtuMessageException, IOException
	{

		Field[] headerFields = getFcHeaderFields();

		JDataInput dataInput = createJDataInput(data);

		JSONObject result = new JSONObject();

		readFields(headerFields, result, dataInput);

		// 判断headJson所属协议
		String protocolNo = result.getString(getFcHeaderKey());

		Template template = getFCTemplate(protocolNo);

		if (!template.isMix()) {

			readFields(template.getFields(), result, dataInput);

			return dealResult(protocolNo, result, rowMapper);

		}

		// 解析First

		readFields(template.getFirstFields(), result, dataInput);

		// 根据第二个协议解析 map fields
		String mixKeyValue = result.getString(template.getMixKey());

		Field[] mixFields = template.getMixInfoFields(mixKeyValue);

		readFields(mixFields, result, dataInput);

		// 解析end
		readFields(template.getEndFields(), result, dataInput);

		return dealResult(getMixDispatchCenterKey(protocolNo, mixKeyValue), result, rowMapper);

	}

}
