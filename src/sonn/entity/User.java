package sonn.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import sonn.annotation.IsValidString;

/**
* @ClassName: User 
* @Description: User实体类
* @author 无名
* @date 2016-3-25 2016-5-1添加主键生成策略
*       2016-11-25 check by annotation
*       2016-12-04 path of h_pic
* @version 1.0
 */
@Entity
public class User
{
	/*id*/
	private int id;
	
	/*用户名*/
	@NotNull
	@IsValidString
	@Length(min=1, max=20)
	private String username;
	
	/*密码*/
	@NotNull
	@IsValidString
	@Length(min=1, max=70)
	private String password;
	
	/*pic path*/
	@IsValidString
	@Length(min=1, max=200)	
	private String h_pic_path;
	
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

	public String getH_pic_path() {
		return h_pic_path;
	}

	public void setH_pic_path(String h_pic_path) {
		this.h_pic_path = h_pic_path;
	}
	
}
