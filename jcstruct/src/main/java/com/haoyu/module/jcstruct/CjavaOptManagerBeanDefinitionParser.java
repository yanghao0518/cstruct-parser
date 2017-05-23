package com.haoyu.module.jcstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.haoyu.module.jcstruct.common.BaseDataType;
import com.haoyu.module.jcstruct.opt.JFieldOpt;
import com.haoyu.module.jcstruct.opt.OptManager;
import com.haoyu.module.jcstruct.utils.DataTypeUtils;

/**
 * 默认解析cjava自定义标签实现
 * 
 * @author DELL
 *
 */
public class CjavaOptManagerBeanDefinitionParser extends AbstractSingleBeanDefinitionParser
{

	private String defaultClass = "com.haoyu.module.jcstruct.opt.DefaultOptManager";
	private String defaultId = "optManager";

	@Override
	protected Class<?> getBeanClass(Element element)
	{
		return OptManager.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder)
	{
		// 是否有class
		if (element.hasAttribute("class") && StringUtils.hasText(element.getAttribute("class"))) {
			// 重新赋值
			builder.getRawBeanDefinition().setBeanClassName(element.getAttribute("class"));
		} else {
			builder.getRawBeanDefinition().setBeanClassName(defaultClass);
		}

		// 设置子项
		List<Element> childElts = DomUtils.getChildElements(element);
		// 解析fields
		if (!CollectionUtils.isEmpty(childElts)) {
			Map<BaseDataType, JFieldOpt> inits = new HashMap<BaseDataType, JFieldOpt>();

			Map<String, String> valueRefs = new HashMap<String, String>();

			for (Element child : childElts) {

				if (child.hasAttribute("key") && StringUtils.hasText(child.getAttribute("key")) && child.hasAttribute("class") && StringUtils.hasText(child.getAttribute("class"))) {
					checkKey(child.getAttribute("key"));
					// 进行实例化
					try {
						checkClass(child.getAttribute("class"));
						Class<?> targetClass = Class.forName(child.getAttribute("class"));
						inits.put(getBaseDataType(child.getAttribute("key")), (JFieldOpt) BeanUtils.instantiate(targetClass));
					} catch (ClassNotFoundException e) {
						throw new RuntimeException("实例化class:" + child.getAttribute("class") + "失败!");
					}
				}

				if (child.hasAttribute("key") && StringUtils.hasText(child.getAttribute("key")) && child.hasAttribute("value-ref") && StringUtils.hasText(child.getAttribute("value-ref"))) {
					checkKey(child.getAttribute("key"));
					valueRefs.put(child.getAttribute("key"), child.getAttribute("value-ref"));
				}

			}

			builder.addConstructorArgValue(inits);

			builder.addPropertyValue("valueRefs", valueRefs);
		}

		dealId(element, builder);

	}

	private void dealId(Element element, BeanDefinitionBuilder builder)
	{

		String id = element.getAttribute("id");
		if (!StringUtils.hasText(id)) {
			// builder.addPropertyValue("id", defaultId);
			element.setAttribute("id", defaultId);
		} else {
			// builder.addPropertyValue("id", id);
		}

	}

	private BaseDataType getBaseDataType(String attribute)
	{
		// TODO Auto-generated method stub
		return DataTypeUtils.getDataType(attribute);
	}

	private void checkClass(String attribute) throws ClassNotFoundException
	{
		// TODO Auto-generated method stub
		Class<?> targetClass = Class.forName(attribute);
		Assert.isAssignable(JFieldOpt.class, targetClass, "this current class is not implement JFieldOpt!");

	}

	private void checkKey(String attribute)
	{

		Assert.notNull(DataTypeUtils.getDataType(attribute), attribute + "对应的C类型枚举不存在！");
	}

}
