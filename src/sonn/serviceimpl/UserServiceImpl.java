package sonn.serviceimpl;

import java.security.Key;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sonn.dao.UserDao;
import sonn.entity.User;
import sonn.service.UserService;
import sonn.util.Principal;
import sonn.util.StringUtill;
import sun.misc.BASE64Encoder;


/**
* @ClassName: UserServiceImpl 
* @Description: User service实现类
* @author 无名
* @date 2016-3-25
*       2016-11-27  check passwd's complexity
*       2016-12-4   getUsernameFromSession
*       2016-12-23 getUserPrincipalFromSession
* @version 1.0
 */
@Transactional
@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService
{
	
	@Resource(name = "userDaoImpl")
	private UserDao userDao;
	
	@Resource(name = "userDaoImpl")
	public void setBaseDao(UserDao userDao)
	{
		super.setBaseDao(userDao);
	}

	@Override
	public List<User> findByUserName(String username) 
	{
		return userDao.findByUserName(username);
	}

    /** 
     * check the passwd
     * 1 at least length = 6 
     * 2 begin with the letter
     * 3 contains special characters 
     * 4 contains numbers
     * @param pwd 
     * @return 
     */  
    public boolean validPwd(String pwd)
    {  
        if(StringUtill.isStringEmpty(pwd)){  
            return false;  
        }  
        if(pwd.length() < 6)
        {  
            return false;  
        }  
//        if(pwd.matches("^[a-zA-z](.*)") &&
//        		pwd.matches("(.*)[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+(.*)")
//        		&& pwd.matches("(.*)\\d+(.*)"))
//        {  
//            return true;  
//        }  
        return true;  
    }  
    
	public String getUsernameFromSession(HttpServletRequest request) {
    	// get username from session
		HttpSession session = request.getSession();
		//get the user who logins
		Principal userPrincipal =
				(Principal) session. getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		if (userPrincipal == null)
			return null;
		String userName = userPrincipal.getUsername();
		if (StringUtill.isStringEmpty(userName))
			return null;	
		return userName;
	}
	
    /**
     * 得到密钥字符串（经过base64编码）
     * @return
     */
    public String getKeyString(Key key) {
          byte[] keyBytes = key.getEncoded();
          String s = (new BASE64Encoder()).encode(keyBytes);
          return s;
    }

    /**
    * @Title: getUserPrincipalFromSession 
    * @Description: get the Pricipal class(id and name) of usr from session
    * @param @param request
    * @param @return    
    * @return Principal    
    * @throws
     */
	@Override
	public Principal getUserPrincipalFromSession(HttpServletRequest request) {
    	// get username from session
		HttpSession session = request.getSession();
		//get the user who logins
		return (Principal)session.getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
	}
    
}
