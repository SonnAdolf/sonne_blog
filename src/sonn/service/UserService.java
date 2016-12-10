package sonn.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sonn.entity.User;


/**
* @ClassName: UserService 
* @Description: User service接口
* @author 无名
* @date 2016-3-25 
*        2016-11-27  check passwd's complexity
*        2016-12-4 getUsernameFromSession
* @version 1.0
 */
public interface UserService extends BaseService<User>
{
	/**
	 *@Title: findByUserName 
	* @Description: 根据用户名查询
	* @param @param username
	* @param @return    设定文件 
	* @return List<User>    返回类型 
	* @throws
	 */
	public List<User> findByUserName(String username);
	
    public boolean validPwd(String pwd);
    
    public String getUsernameFromSession(HttpServletRequest request);
}
