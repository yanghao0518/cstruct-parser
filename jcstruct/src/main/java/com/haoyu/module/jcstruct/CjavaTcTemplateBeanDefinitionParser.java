package com.haoyu.module.jcstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.haoyu.module.jcstruct.model.Field;

/**
 * 默认解析cjava自定义标签实现
 * 
 * @author DELL
 *
 */
public class CjavaTcTemplateBeanDefinitionParser extends AbstractTemplateBeanDefinitionParser
{

	@Override
	protected Class<?> getBeanClass(Element element)
	{
		return Template.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder)
	{

		setDefault(element, builder);

		String className = element.getAttribute("class");
		if (StringUtils.hasText(className)) {
			builder.getBeanDefinition().setBeanClassName(className);
		}

		List<Element> childElts = DomUtils.getChildElements(element);
		// 解析fields
		if (CollectionUtils.isEmpty(childElts)) {
			throw new IllegalArgumentException("cjava:tct node must contain fields");
		}

		// 判断是否存在混合协议
		boolean isMix = isMix(childElts);
		if (!isMix) {
			parserFieldCommon(builder, childElts);
			return;
		}

		int mixIndex = -1;

		List<Element> childFirstElts = new ArrayList<Element>();

		List<Element> childEndFields = new ArrayList<Element>();

		for (int i = 0; i < childElts.size(); i++) {
			Element node = childElts.get(i);
			// 解析
			if (node.hasAttribute("cjava:branchs")) {
				mixIndex = i;
				parserFieldMixBranchs(builder, node);
			} else {
				if (mixIndex != -1) {

					childEndFields.add(node);
				} else {

					childFirstElts.add(node);
				}

			}
		}

		parserFieldMixCommonFirst(builder, childFirstElts);

		parserFieldMixCommonEnd(builder, childEndFields);

	}

	private void parserFieldCommon(BeanDefinitionBuilder builder, List<Element> childElts)
	{
		Field[] values = new Field[childElts.size()];
		for (int i = 0; i < childElts.size(); i++) {
			Element node = childElts.get(i);
			// 解析
			values[i] = parserField(node,false);
		}
		builder.addPropertyValue("fields", values);
		builder.addPropertyValue("isMix", false);
	}

	private void parserFieldMixCommonFirst(BeanDefinitionBuilder builder, List<Element> eles)
	{
		if (!CollectionUtils.isEmpty(eles)) {

			Field[] firstFields = new Field[eles.size()];
			for (int i = 0; i < eles.size(); i++) {
				firstFields[i] = parserField(eles.get(i),false);
			}
			builder.addPropertyValue("firstFields", firstFields);
		}
	}

	private void parserFieldMixCommonEnd(BeanDefinitionBuilder builder, List<Element> eles)
	{
		if (!CollectionUtils.isEmpty(eles)) {

			Field[] endFields = new Field[eles.size()];
			for (int i = 0; i < eles.size(); i++) {
				endFields[i] = parserField(eles.get(i),false);
			}
			builder.addPropertyValue("endFields", endFields);
		}

	}

	private void parserFieldMixBranchs(BeanDefinitionBuilder builder, Element ele)
	{
		List<Element> childElts = DomUtils.getChildElements(ele);

		checkChildBranchs(childElts);

		Map<String, Field[]> mixInfo = new HashMap<String, Field[]>();

		for (int i = 0; i < childElts.size(); i++) {
			Element node = childElts.get(i);
			// 解析branch
			parserBranch(mixInfo, node);
		}

		builder.addPropertyValue("isMix", true);

		builder.addPropertyValue("mixInfo", mixInfo);
	}

	private void checkChildBranchs(List<Element> childElts)
	{
		// 解析fields
		if (CollectionUtils.isEmpty(childElts)) {
			throw new IllegalArgumentException("cjava:branchs node must contain child branch");
		}

		for (int i = 0; i < childElts.size(); i++) {
			Element nodei = childElts.get(i);
			if (!StringUtils.hasText(nodei.getAttribute("keyValue"))) {
				throw new IllegalArgumentException("branch not attr keyValue or keyValue is empty!");
			}
		}

		for (int i = 0; i < childElts.size() - 1; i++) {
			Element nodei = childElts.get(i);
			for (int j = i + 1; j < childElts.size(); j++) {
				Element nodej = childElts.get(j);
				if (nodei.getAttribute("keyValue").equals(nodej.getAttribute("keyValue"))) {

					throw new IllegalArgumentException("branchs has same branch keyValue:" + nodei.getAttribute("keyValue"));

				}
			}
		}
	}

	private void parserBranch(Map<String, Field[]> mixInfo, Element ele)
	{
		List<Element> childElts = DomUtils.getChildElements(ele);
		// 解析fields
		if (CollectionUtils.isEmpty(childElts)) {
			throw new IllegalArgumentException("cjava:branch node must contain child fields");
		}
		Field[] values = new Field[childElts.size()];
		for (int i = 0; i < childElts.size(); i++) {
			Element node = childElts.get(i);
			// 解析field
			values[i] = parserField(node,false);
		}

		mixInfo.put(ele.getAttribute("keyValue"), values);

	}

	private boolean isMix(List<Element> childElts)
	{
		boolean isMix = false;
		for (int i = 0; i < childElts.size(); i++) {
			Element node = childElts.get(i);
			if (!isMix && node.hasAttribute("cjava:branchs")) {
				isMix = true;
				break;
			}
		}
		return false;
	}

	private void setDefault(Element element, BeanDefinitionBuilder builder)
	{
		String name = element.getAttribute("name");
		if (StringUtils.hasText(name)) {
			builder.addPropertyValue("name", name);
			element.setAttribute("name", name);
		} else {
			throw new IllegalArgumentException("tc-template:name is not allow empty!");
		}

		String id = element.getAttribute("id");

		if (StringUtils.hasText(id)) {
			builder.addPropertyValue("id", id);
			element.setAttribute("id", Template.TC_SUFFIX + id);
		} else {
			throw new IllegalArgumentException("tc-template:id is not allow empty!");
		}

	}

}
