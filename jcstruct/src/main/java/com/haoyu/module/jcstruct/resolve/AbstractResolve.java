package com.haoyu.module.jcstruct.resolve;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.Template;
import com.haoyu.module.jcstruct.model.DefaultResolveResult;
import com.haoyu.module.jcstruct.model.Field;
import com.haoyu.module.jcstruct.model.ResolveResult;
import com.haoyu.module.jcstruct.opt.OptManager;
import com.haoyu.module.jcstruct.read.JDataInput;
import com.haoyu.module.jcstruct.read.JDataInputFactoryProxy;
import com.haoyu.module.jcstruct.refresh.RefreshService;
import com.haoyu.module.jcstruct.template.TemplateContainer;
import com.haoyu.module.jcstruct.writer.JDataOutput;
import com.haoyu.module.jcstruct.writer.JDataOutputFactoryProxy;

public abstract class AbstractResolve implements RefreshService
{

	@Autowired
	private OptManager optManager;

	@Autowired
	private TemplateContainer templateContainer;

	private List<UnResolvePostProcess> unResolvePostProcessList;

	private List<UnResolveFieldPostProcess> unResolveFieldPostProcessList;

	@Override
	public void refresh(ApplicationContext applicationContext)
	{
		unResolvePostProcessList = new ArrayList<>();

		Map<String, UnResolvePostProcess> unResolvePostProcessMap = applicationContext.getBeansOfType(UnResolvePostProcess.class);

		if (!CollectionUtils.isEmpty(unResolvePostProcessMap)) {
			unResolvePostProcessList.addAll(unResolvePostProcessMap.values());
		}
		unResolvePostProcessMap = null;

		unResolveFieldPostProcessList = new ArrayList<>();

		Map<String, UnResolveFieldPostProcess> unResolveFieldPostProcessMap = applicationContext.getBeansOfType(UnResolveFieldPostProcess.class);

		if (!CollectionUtils.isEmpty(unResolveFieldPostProcessMap)) {
			unResolveFieldPostProcessList.addAll(unResolveFieldPostProcessMap.values());
		}
		unResolveFieldPostProcessMap = null;

	}

	protected void beforeUnresolve(JSONObject unResolveMessage)
	{

		for (UnResolvePostProcess postProcess : unResolvePostProcessList) {
			postProcess.beforePostProcess(unResolveMessage);
		}
	}

	protected void afterUnresolve(JSONObject unResolveMessage, JDataOutput dataOutput)
	{
		for (UnResolvePostProcess postProcess : unResolvePostProcessList) {
			postProcess.afterPostProcess(unResolveMessage, dataOutput);
		}
	}

	protected Field[] getFcHeaderFields()
	{
		return templateContainer.getFcHeader().getFields();
	}

	protected Field[] getTcHeaderFields()
	{
		return templateContainer.getTcHeader().getFields();
	}

	protected String getFcHeaderKey()
	{
		return templateContainer.getFcHeader().getKey();
	}

	protected String getTcHeaderKey()
	{
		return templateContainer.getTcHeader().getKey();
	}

	protected int getTcHeaderLength()
	{
		return templateContainer.getTcHeader().getByteLength();
	}

	protected Template getFCTemplate(String tid)
	{
		Template template = templateContainer.getFCTemplate(tid);
		Assert.notNull(template, "根据协议号:" + tid + "获取定义接收模板不存在!");
		return template;
	}

	protected Template getTCTemplate(String tid)
	{
		Template template = templateContainer.getTCTemplate(tid);
		Assert.notNull(template, "根据协议号:" + tid + "获取定义返回模板不存在!");
		return template;
	}

	protected JDataInput createJDataInput(byte[] data)
	{
		return JDataInputFactoryProxy.createJDataInput(data);
	}

	protected JDataOutput createJDataOutput()
	{
		return JDataOutputFactoryProxy.createJDataOutput();
	}

	protected <T> ResolveResult<T> dealResult(final String id, final JSONObject result, final ResolveResultRowMapper<T> rowMapper)
	{
		if (null != rowMapper) {

			return new DefaultResolveResult<T>(id, rowMapper.mapRow(result));

		}
		return (ResolveResult<T>) new DefaultResolveResult<JSONObject>(id, result);
	}

	protected void readFields(Field[] fields, JSONObject result, JDataInput dataInput) throws IOException
	{
		for (Field field : fields) {
			result.put(field.getName(), optManager.getJFieldOpt(field.getType()).read(field, dataInput, result));
		}
	}

	protected void writerFields(Field[] fields, JSONObject content, JDataOutput dataOutput) throws IOException
	{
		for (Field field : fields) {
			for (UnResolveFieldPostProcess process : unResolveFieldPostProcessList) {
				process.postProcess(field, content, dataOutput);
			}
			optManager.getJFieldOpt(field.getType()).writer(content, field, dataOutput);
		}
	}

	protected String getMixDispatchCenterKey(String id1, String id2)
	{
		return id1 + "_" + id2;
	}
}
