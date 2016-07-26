package sonn.util;

/**
* @ClassName: Principal 
* @Description:身份信息
* @author 无名
* @date 2016-5-2 下午2:32:46 
* @version 1.0
 */
public class Principal
{
	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(int id, String username)
	{
		this.id = id;
		this.username = username;
	}
	
	/* ID */
	private int id;

	/* 用户名 */
	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
