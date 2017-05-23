package com.haoyu.module.jcstruct.template;

import com.haoyu.module.jcstruct.Header;
import com.haoyu.module.jcstruct.Template;

public interface TemplateContainer
{
	public Template getFCTemplate(String tempId); 
	
	public Template getTCTemplate(String tempId); 
	
	public Header getFcHeader();
	
	public Header getTcHeader();
}
