package com.haoyu.module.jcstruct.refresh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class RefreshServiceManager extends AbstractRefreshServiceManager
{


	@Autowired
	private List<RefreshService> refreshServices;

	public void refresh(ApplicationContext applicationContext) 
	{
		for (RefreshService refreshService : refreshServices)
			refreshService.refresh(applicationContext);
	}

}
