package sonn.service;

import java.security.Key;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sonn.entity.User;
import sonn.util.Principal;


/**
* @ClassName: UserService 
* @Description: User service interface
* @author sonne
* @date 2016-3-25 
*        2016-11-27  check passwd's complexity
*        2016-12-4 getUsernameFromSession
*        2016-12-23 getUserPrincipalFromSession
* @version 1.0
 */
public interface UserService extends BaseService<User>
{
	/**
	 *@Title: findByUserName 
	* @Description: search the user by name
	* @param @param username
	* @param @return    
	* @return List<User>     
	* @throws
	 */
	public List<User> findByUserName(String username);
	
    public boolean validPwd(String pwd);
    
    public String getUsernameFromSession(HttpServletRequest request);
    
    /**
    * @Title: getUserPrincipalFromSession 
    * @Description: get the Pricipal class(id and name) of usr from session
    * @param @param request
    * @param @return    
    * @return Principal    
    * @throws
     */
    public Principal getUserPrincipalFromSession(HttpServletRequest request);
    
    public String getKeyString(Key key);
    
    public void check_auto_login(HttpServletRequest request);
}
