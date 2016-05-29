package com.serviceimpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.dao.BaseDao;
import com.entity.Article;
import com.service.BaseService;
import com.util.Page;
import com.util.PageInfo;
import com.util.Principal;

/**
* @ClassName: BaseServiceImpl 
* @Description:service父类
* @author 无名
* @date 2016-4-22
* @version 1.0
* @param <T>
 */
@Transactional
public class BaseServiceImpl<T> implements BaseService<T>
{
	/** baseDao */
	private BaseDao<T> baseDao;

	public void setBaseDao(BaseDao<T> baseDao)
	{
		this.baseDao = baseDao;
	}

	@Override
	public Page<T> findPage(PageInfo pageInfo,Class<T> clazz) 
	{
		return baseDao.findPage(pageInfo,clazz);
	}

	/**
	* @Title: findList
	* @Description: 查询所有，以List形式返回
	* @param clazz
	* @return 
	* @see com.service.BaseService#findList(java.lang.Class)
	 */
	@Override
	public List<T> findList(Class<T> clazz) 
	{
		return baseDao.findList(clazz);
	}
	
	public void save(T entity)
	{
		baseDao.persist(entity);
	}

}
