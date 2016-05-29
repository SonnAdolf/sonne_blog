package com.message.bean;

/**
* @ClassName: SimpleBackMessage 
* @Description: 最简单的方法返回的bean
* @author 无名
* @date 2016-5-1 上午9:02:53 2016.05.02RegisterMessage改为SimpleBackMessage
* @version 1.0
 */
public class SimpleBackMessage
{
	
	public SimpleBackMessage()
	{
		this.isSuccess = true;
	}
	
	/*是否注册成功*/
	private boolean isSuccess;
	
	/*返回的提示*/
	private String returnMessage;

	public boolean isSuccess() 
	{
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess)
	{
		this.isSuccess = isSuccess;
	}

	public String getReturnMessage()
	{
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) 
	{
		this.returnMessage = returnMessage;
	}
	
}
