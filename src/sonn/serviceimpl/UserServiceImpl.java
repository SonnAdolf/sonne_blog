package sonn.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sonn.dao.UserDao;
import sonn.entity.User;
import sonn.service.UserService;


/**
* @ClassName: UserServiceImpl 
* @Description: User service实现类
* @author 无名
* @date 2016-3-25
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

}
