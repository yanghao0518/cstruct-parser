package com.haoyu.module.jcstruct.opt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.haoyu.module.jcstruct.common.BaseDataType;
import com.haoyu.module.jcstruct.refresh.RefreshService;
import com.haoyu.module.jcstruct.utils.DataTypeUtils;

public abstract class AbstractOptManager extends HashMap<BaseDataType, JFieldOpt>implements RefreshService, OptManager
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, String> valueRefs = null;

	public abstract void setDefaultRead(BaseDataType type);

	Logger LOG = Logger.getLogger(this.getClass());

	@Override
	public JFieldOpt getJFieldOpt(BaseDataType type)
	{
		return this.get(type);
	}

	public Map<String, String> getValueRefs()
	{
		return valueRefs;
	}

	public void setValueRefs(Map<String, String> valueRefs)
	{
		this.valueRefs = valueRefs;
	}

	public AbstractOptManager()
	{
		BaseDataType[] values = BaseDataType.values();
		for (final BaseDataType baseDataType : values) {
			setDefaultRead(baseDataType);
		}
	}

	public AbstractOptManager(Map<BaseDataType, JFieldOpt> inits)
	{
		super(inits);
		BaseDataType[] values = BaseDataType.values();
		for (final BaseDataType baseDataType : values) {
			if (!this.containsKey(baseDataType)) {
				setDefaultRead(baseDataType);
			}
		}
	}

	@Override
	public void refresh(ApplicationContext applicationContext)
	{

		try {
			if (!CollectionUtils.isEmpty(valueRefs)) {

				Iterator<Map.Entry<String, String>> iterators = valueRefs.entrySet().iterator();

				while (iterators.hasNext()) {

					Map.Entry<String, String> entry = iterators.next();

					// 得到key得到value ref
					checkKey(entry.getKey());

					Object obj = applicationContext.getBean(entry.getValue());

					checkValue(obj);

					this.put(getBaseDataType(entry.getKey()), (JFieldOpt) obj);

				}

			}
		} catch (Exception e) {
			LOG.error("初始化Optmanager失败！原因注入valueref失败：" + e.getMessage(), e);
		}
	}

	private BaseDataType getBaseDataType(String attribute)
	{
		return DataTypeUtils.getDataType(attribute);
	}

	private void checkValue(Object obj) throws ClassNotFoundException
	{

		Assert.isInstanceOf(JFieldOpt.class, obj, "this current value ref is not implement JFieldOpt!");

	}

	private void checkKey(String attribute)
	{
		Assert.notNull(DataTypeUtils.getDataType(attribute), attribute + "对应的C类型枚举不存在！");
	}
}
