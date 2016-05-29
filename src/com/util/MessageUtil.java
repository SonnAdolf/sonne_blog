package com.util;

import com.alibaba.fastjson.JSONObject;
import com.message.bean.SimpleBackMessage;

/**
* @ClassName: MessageUtil 
* @Description: 处理json信息
* @author 无名
* @date 2016-5-2 下午12:36:14 
* @version 1.0
 */
public final class MessageUtil 
{
	private MessageUtil()
	{
	}
	
	public static void setSimpleIsSuccessJSON(JSONObject jo,boolean isSuccess)
	{
		jo.put("success", isSuccess);
	}
	
    public static void setJSONObject(JSONObject jo,SimpleBackMessage sbm)
    {
		jo.put("success", sbm.isSuccess());
		jo.put("returnMessage", sbm.getReturnMessage());
    }
    
    public static void setJOWhenIputWrong(JSONObject jo)
    {
		jo.put("success", false);
		jo.put("returnMessage", "input wrong!");
    }
    
    public static void setSimpleBackMessage
                   (SimpleBackMessage sbm,boolean success,String info)
    {
		sbm.setSuccess(success);
		sbm.setReturnMessage(info);
    }
}
