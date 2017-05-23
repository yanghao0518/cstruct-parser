package com.haoyu.module.jcstruct.template;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.haoyu.module.jcstruct.Header;
import com.haoyu.module.jcstruct.Template;
import com.haoyu.module.jcstruct.common.SystemConsts;

public class DefaultTemplateContainer implements TemplateContainer, BeanFactoryAware
{
	private BeanFactory beanFactory;

	@Override
	public Template getFCTemplate(String tempId)
	{
		return beanFactory.getBean(Template.FC_SUFFIX + tempId, Template.class);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException
	{
		this.beanFactory = beanFactory;
	}

	@Override
	public Template getTCTemplate(String tempId)
	{
		return beanFactory.getBean(Template.TC_SUFFIX +tempId, Template.class);
	}

	@Override
	public Header getFcHeader()
	{
		return beanFactory.getBean(SystemConsts.FC_HEADER_DEFAULT_ID, Header.class);
	}

	@Override
	public Header getTcHeader()
	{
		return beanFactory.getBean(SystemConsts.TC_HEADER_DEFAULT_ID, Header.class);
	}

}
