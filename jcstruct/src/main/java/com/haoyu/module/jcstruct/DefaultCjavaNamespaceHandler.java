package com.haoyu.module.jcstruct;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class DefaultCjavaNamespaceHandler extends NamespaceHandlerSupport
{

	@Override
	public void init()
	{
		// 注册cjava标签的的解析器
		registerBeanDefinitionParser("fch", new CjavaFcHeaderBeanDefinitionParser());

		registerBeanDefinitionParser("tch", new CjavaTcHeaderBeanDefinitionParser());

		registerBeanDefinitionParser("check", new CjavaCheckBeanDefinitionParser());

		registerBeanDefinitionParser("tct", new CjavaTcTemplateBeanDefinitionParser());

		registerBeanDefinitionParser("fct", new CjavaFcTemplateBeanDefinitionParser());
		
		//加入默认标签值
		
		registerBeanDefinitionParser("templateContainer", new CjavaTemplateContaineBeanDefinitionParser());
		
		registerBeanDefinitionParser("optManager", new CjavaOptManagerBeanDefinitionParser());
		
		registerBeanDefinitionParser("dispatchCenterService", new CjavaDispatchCenterManagerBeanDefinitionParser());
		
		registerBeanDefinitionParser("refreshManager", new CjavaRefreshManagerBeanDefinitionParser());
		
		

	}

}
