package com.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dao.UserDao;
import com.entity.User;
import com.util.StringUtill;

/**
* @ClassName: UserDaoImpl 
* @Description: User dao实现类
* @author 无名
* @date 2016-3-25 
* @version 1.0
 */
@Transactional
@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao 
{
	
	@PersistenceContext
	protected EntityManager entityManager;

	/**
	* @Title: findByUserName
	* @Description: 根据用户名查询
	* @param username
	* @return 
	* @see com.dao.UserDao#findByUserName(java.lang.String)
	 */
	@Override
	public List<User> findByUserName(String username)
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (!StringUtill.isStringEmpty(username)) 
		{
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("username"), username));
		}
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

}
