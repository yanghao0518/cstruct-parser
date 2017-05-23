package com.haoyu.module.jcstruct;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import com.haoyu.module.jcstruct.check.CJavaCheck;

/**
 * 默认解析cjava自定义标签实现
 * 
 * @author DELL
 *
 */
public class CjavaCheckBeanDefinitionParser extends AbstractSingleBeanDefinitionParser
{

	@Override
	protected Class<?> getBeanClass(Element element)
	{
		return CJavaCheck.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder)
	{

		String name = element.getAttribute("name");
		builder.addPropertyValue("name", name);
		String id = element.getAttribute("id");
		builder.addPropertyValue("id", id);
		String className = element.getAttribute("class");
		builder.getBeanDefinition().setBeanClassName(className);
	}
}
