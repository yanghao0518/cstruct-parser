package com.haoyu.module.jcstruct;

import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.haoyu.module.jcstruct.common.BaseDataType;
import com.haoyu.module.jcstruct.model.Field;
import com.haoyu.module.jcstruct.utils.DataTypeUtils;
import com.haoyu.module.jcstruct.utils.MatchUtils;

public abstract class AbstractTemplateBeanDefinitionParser extends AbstractSingleBeanDefinitionParser
{

	// 解析具体列
	protected Field parserField(Element node,boolean isForReceive)
	{
		Field field = new Field();
		field.setName(node.getAttribute("name"));

		BaseDataType dataType = null;
		String parserType = node.getAttribute("type");
		if (null != parserType) {
			dataType = DataTypeUtils.getDataType(parserType);
		}
		Assert.notNull(dataType, "not found defined data type  or parserType please check enum BaseDataType");
		field.setType(dataType);

		String defaultValue = node.getAttribute("defaultValue");
		// 如果有默认值
		if (StringUtils.hasText(defaultValue)) {
			
			Object def = DataTypeUtils.getBaseDateValue(dataType, defaultValue);
			
			if (def != null) {
				field.setDefaultValue(def);
			}
		}
		
		//处理属性规则
		String realValueRule = node.getAttribute("realValueRule");
		if (StringUtils.hasText(realValueRule)) {
			//此属性只匹配 short ushort int uint long float double
			Assert.isTrue(checkRealValueRuleValidType(field.getType()), "不符合此规则的类型!");
			
			field.setSimpleValueRule(MatchUtils.matchValue(realValueRule,isForReceive));
		}
		
		//处理长度规则
		String realLengthRule = node.getAttribute("realLengthRule");
		if (StringUtils.hasText(realLengthRule)) {
			field.setSimpleLengthRule(MatchUtils.matchLength(realLengthRule));
		}

		return field;
	}

	private boolean checkRealValueRuleValidType(BaseDataType type){
		
		return type == BaseDataType.FLOAT || type == BaseDataType.SHORT || type == BaseDataType.USHORT || type == BaseDataType.INT
				|| type == BaseDataType.UINT || type == BaseDataType.FLOAT || type == BaseDataType.DOUBLE || type == BaseDataType.LONG;
	}

}
