package com.haoyu.module.jcstruct;

import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.haoyu.module.jcstruct.common.SystemConsts;
import com.haoyu.module.jcstruct.model.Field;



/**
 * 默认解析cjava自定义标签实现
 * 
 * @author DELL
 *
 */
public class CjavaFcHeaderBeanDefinitionParser extends AbstractTemplateBeanDefinitionParser
{

	
	@Override
	protected Class<?> getBeanClass(Element element)
	{
		return Header.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder)
	{

		String key = element.getAttribute("key");
		builder.addPropertyValue("key", key);
		
		setDefault(element,builder);
		
		String className = element.getAttribute("class");
		if (StringUtils.hasText(className)) {
			builder.getBeanDefinition().setBeanClassName(className);
		}

		List<Element> childElts = DomUtils.getChildElements(element);
		// 解析fields
		if (CollectionUtils.isEmpty(childElts)) {
			throw new IllegalArgumentException("cjava:header node must contain fields");
		}

		Field[] values = new Field[childElts.size()];
		for (int i = 0; i < childElts.size(); i++) {
			Element node = childElts.get(i);
			// 解析
			values[i] = parserField(node,true);
		}
		builder.addPropertyValue("fields", values);

	}
	
	private void setDefault(Element element,BeanDefinitionBuilder builder){
		String name = element.getAttribute("name");
		if(!StringUtils.hasText(name)){
			builder.addPropertyValue("name", SystemConsts.FC_HEADER_DEFAULT_ID);
			element.setAttribute("name", SystemConsts.FC_HEADER_DEFAULT_NAME);
		}else{
			builder.addPropertyValue("name", name);
		}
		
		String id = element.getAttribute("id");
		if(!StringUtils.hasText(id)){
			builder.addPropertyValue("id", SystemConsts.FC_HEADER_DEFAULT_ID);
			element.setAttribute("id", SystemConsts.FC_HEADER_DEFAULT_NAME);
		}else{
			builder.addPropertyValue("id", id);
		}
	}


}
