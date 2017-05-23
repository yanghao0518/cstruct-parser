package com.haoyu.module.jcstruct.handle;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.model.DefaultResponseResult;

/**
 * 处理接受来自C＋＋消息结构服务类
 * @author yanghao0518
 *
 */
public interface HandleMessageService {

	public void handle(JSONObject message,DefaultResponseResult responseResult);
}
