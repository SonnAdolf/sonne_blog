package sonn.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @ClassName: User 
* @Description: User实体类
* @author 无名
* @date 2016-3-25 2016-5-1添加主键生成策略
*       2016-11-25 check by annotation
*       2016-12-04 path of h_pic
*       2016-12-14 add the date of registering sonne blog
* @version 1.0
 */
@Entity
public class User
{
	/*id*/
	private int id;
	
	/*用户名*/
	@NotNull
	@Length(min=1, max=20)
	private String username;
	
	/*密码*/
	@NotNull
	@Length(min=1, max=70)
	private String password;
	
	/*pic path*/
	@Length(min=1, max=200)	
	private String h_pic_path;
	
	/*date of registering sonne blog*/
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date blog_date;
	
	private int fans_num;
	
	private int following_num;
	
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

	public Date getBlog_date() {
		return blog_date;
	}

	public void setBlog_date(Date blog_date) {
		this.blog_date = blog_date;
	}

	public int getFans_num() {
		return fans_num;
	}

	public void setFans_num(int fans_num) {
		this.fans_num = fans_num;
	}

	public int getFollowing_num() {
		return following_num;
	}

	public void setFollowing_num(int following_num) {
		this.following_num = following_num;
	}
	
}
