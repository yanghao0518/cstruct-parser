package com.haoyu.module.jcstruct;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.haoyu.module.jcstruct.template.TemplateContainer;

/**
 * 默认解析cjava自定义标签实现
 * 
 * @author DELL
 *
 */
public class CjavaTemplateContaineBeanDefinitionParser extends AbstractSingleBeanDefinitionParser
{

	private String defaultClass = "com.haoyu.module.jcstruct.template.DefaultTemplateContainer";
	private String defaultId = "templateContainer";

	@Override
	protected Class<?> getBeanClass(Element element)
	{
		return TemplateContainer.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder)
	{
		// 是否有class
		if (element.hasAttribute("class") && StringUtils.hasText(element.getAttribute("class"))) {
			// 重新赋值
			checkClass(element.getAttribute("class"));
			builder.getRawBeanDefinition().setBeanClassName(element.getAttribute("class"));
		} else {
			builder.getRawBeanDefinition().setBeanClassName(defaultClass);
		}

		dealId(element,builder);
	}
	
	private void checkClass(String attribute) 
	{
		Class<?> targetClass = null;
		try {
			targetClass = Class.forName(attribute);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("not found class:" + attribute);
		}
		Assert.isAssignable(TemplateContainer.class, targetClass, "this current class is not implement RefreshServiceManagerService!");
		

	}
	
	private void dealId(Element element, BeanDefinitionBuilder builder)
	{

		String id = element.getAttribute("id");
		if (!StringUtils.hasText(id)) {
			//builder.addPropertyValue("id", defaultId );
			element.setAttribute("id", defaultId);
		} else {
			//builder.addPropertyValue("id", id);
		}

	}

}
