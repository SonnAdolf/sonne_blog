package sonn.dao;

import java.util.List;

import sonn.entity.User;


/**
* @ClassName: UserDao 
* @Description: User dao interface
* @author sonne
* @date 2016-3-25
* @version 1.0
 */
public interface UserDao extends BaseDao<User>
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
}
