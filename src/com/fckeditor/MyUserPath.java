package com.fckeditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.fckeditor.requestcycle.UserPathBuilder;

/**
* @ClassName: MyUserPath 
* @Description: 设置fckeditor user路径
* @author 无名
* @date 2016-5-15 下午12:10:56 
* @version 1.0
 */
public class MyUserPath implements UserPathBuilder
{

	public String getUserFilesPath(HttpServletRequest request) 
	{
		//返回一个路径，这个路径就是用户的目录
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		return "/userfiles/" + username;
	}

}
