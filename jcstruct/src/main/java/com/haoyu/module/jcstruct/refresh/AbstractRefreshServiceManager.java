package com.haoyu.module.jcstruct.refresh;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public abstract class AbstractRefreshServiceManager implements RefreshServiceManagerService, ApplicationListener<ContextRefreshedEvent>
{

	Logger LOG = Logger.getLogger(this.getClass());
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if (event.getApplicationContext().getParent() == null) {
			try {
				refresh(event.getApplicationContext());
			} catch (Exception e) {
				LOG.error("初始化加载HandleMessageService处理失败!", e);
				System.exit(0);
			}

		}
	}


	abstract public void refresh(ApplicationContext applicationContext);

	
}
