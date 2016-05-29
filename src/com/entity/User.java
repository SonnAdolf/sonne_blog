package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
* @ClassName: User 
* @Description: User实体类
* @author 无名
* @date 2016-3-25 2016-5-1添加主键生成策略
* @version 1.0
 */
@Entity
public class User
{
	/*id*/
	private int id;
	
	/*用户名*/
	private String username;
	
	/*密码*/
	private String password;
	
	/* "身份信息"参数名称 */
	public static final String PRINCIPAL_ATTRIBUTE_NAME 
	                       = User.class.getName() + ".PRINCIPAL";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
}
