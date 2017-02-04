package sonn.service;

import java.util.List;

import sonn.Order;
import sonn.util.Page;
import sonn.util.PageInfo;


/**
* @ClassName: BaseService 
* @Description:Service父接口
* @author sonne
* @date 2016-4-22
*       2016-11-27 order
*       2017-02-02 updates
* @version 1.0
* @param <T>
 */
public interface BaseService<T>
{
	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<T> findPage(PageInfo pageInfo,Class<T> clazz, List<Order> orders);
	
	/**
	 * @Title: findList 
	* @Description: 查询所有，以List形式返回
	* @param @param clazz
	* @param @return    设定文件 
	* @return List<T>    返回类型 
	* @throws
	 */
	List<T> findList(Class<T> clazz,  List<Order> orders);
	
	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void save(T entity);


	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	T update(T entity);
	
	/**
	 * 更新实体对象集合
	 * 
	 * @param entities
	 *            实体对象集合
	 * @return 实体对象集合
	 */
	List<T> updates(List<T> entities);
	
	/**
	* @Title: find 
	* @Description: 根据id查询
	* @param @param id
	* @param @return    设定文件 
	* @return T    返回类型 
	* @throws
	 */
	T find(Integer id,Class<T> clazz);


	/**
	 * 删除实体对象
	 * 
	 * @param id
	 *            ID
	 */
	void delete(Integer id, Class<T> clazz);


	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void delete(T entity);

}
