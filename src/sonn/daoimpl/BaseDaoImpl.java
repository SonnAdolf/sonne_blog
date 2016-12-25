package sonn.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.util.Assert;

import sonn.Order;
import sonn.Order.Direction;
import sonn.dao.BaseDao;
import sonn.util.Page;
import sonn.util.PageInfo;
import sonn.util.PageUtil;


/**
* @ClassName: BaseDaoImpl 
* @Description: dao base class
* @author sonne
* @date 2016-4-22  2016-05-21findPage logical error fix
*                  2016-11-27 order
* @version 1.0
* @param <T>
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T>
{
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	/** ±ðÃûÊý */
	private static volatile long aliasCount = 0;
	
	public T find(Integer id,Class<T> clazz)
	{
		if (id != null) 
		{
			return entityManager.find(clazz, id);
		}
		return null;
	}
	
	@Override
	public List<T> findList(Class<T> clazz, List<Order> orders)
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());
		Root<T> root = criteriaQuery.from(clazz);
		addOrders(criteriaQuery, orders);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

	private void addOrders(CriteriaQuery<T> criteriaQuery, List<Order> orders) {
		if (criteriaQuery == null || orders == null || orders.isEmpty()) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty()) {
			orderList.addAll(criteriaQuery.getOrderList());
		}
		for (Order order : orders) {
			if (order.getDirection() == Direction.asc) {
				orderList.add(criteriaBuilder.asc(root.get(order.getProperty())));
			} else if (order.getDirection() == Direction.desc) {
				orderList.add(criteriaBuilder.desc(root.get(order.getProperty())));
			}
		}
		criteriaQuery.orderBy(orderList);
	}
	
	@Override
	public Page<T> findPage(PageInfo pageInfo,Class<T> clazz, List<Order> orders) 
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());
		addOrders(criteriaQuery, orders);
		return findPage(criteriaQuery, pageInfo,clazz);
	}
	
	@Override
	public Page<T> findPage(CriteriaQuery<T> criteriaQuery,PageInfo pageInfo,
			Class<T> clazz, List<Order> orders) 
	{
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());
		addOrders(criteriaQuery, orders);
		return findPage(criteriaQuery, pageInfo,clazz);
	}
	
	
	protected Page<T> findPage(CriteriaQuery<T> criteriaQuery, 
			                  PageInfo pageInfo,Class<T> clazz) 
	{
		long total = count(criteriaQuery,clazz);
		List<T> list = null;
		if (total > 0)
		{
			if(null == pageInfo)
			{
				pageInfo = PageUtil.createPage(PageInfo.DEFAULT_EVERYOAGE, total,
						PageInfo.DEFAULT_CURRENTPAGE);
			}
			else
			{
				PageUtil.setPageInfo(pageInfo,pageInfo.getEveryPage(),total,
						 pageInfo.getCurrentPage());
			}
		}
		else
		{
			pageInfo.setCurrentPage(1);
		}
		list = getPagedQueryList(criteriaQuery,pageInfo);
		return new Page<T>(list, total, pageInfo);
	}
	
	private List<T> getPagedQueryList(CriteriaQuery<T> criteriaQuery, PageInfo pageInfo)
	{
		int everyPage = pageInfo.getEveryPage();
        TypedQuery<T> query = 
        		entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
        query.setFirstResult((pageInfo.getCurrentPage()-1)  * everyPage);
        query.setMaxResults(everyPage);
        List<T> list = query.getResultList();	
        return list;
	}
	
	protected long count(CriteriaQuery<T> criteriaQuery,Class<T> clazz) 
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		for (Root<?> root : criteriaQuery.getRoots()) 
		{
			Root<?> dest = countCriteriaQuery.from(root.getJavaType());
			dest.alias(getAlias(root));
			copyJoins(root, dest);
		}
		Root<?> countRoot = getRoot(countCriteriaQuery, criteriaQuery.getResultType());
		countCriteriaQuery.select(criteriaBuilder.count(countRoot));
		if (criteriaQuery.getRestriction() != null) 
		{
			countCriteriaQuery.where(criteriaQuery.getRestriction());
		}
		return entityManager.createQuery(countCriteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	}
	
	@SuppressWarnings("unused")
	private Root<T> getRoot(CriteriaQuery<T> criteriaQuery) {
		if (criteriaQuery != null) {
			return getRoot(criteriaQuery, criteriaQuery.getResultType());
		}
		return null;
	}

	private Root<T> getRoot(CriteriaQuery<?> criteriaQuery, Class<T> clazz) 
	{
		if (criteriaQuery != null && criteriaQuery.getRoots() != null && clazz != null)
		{
			Set<Root<?>> set = criteriaQuery.getRoots();
			for (Root<?> root : set) 
			{
				if (clazz.equals(root.getJavaType()))
				{
					return (Root<T>) root.as(clazz);
				}
			}
		}
		return null;
	}

	
	protected void copyJoins(From<?, ?> from, From<?, ?> to)
	{
		for (Join<?, ?> join : from.getJoins())
		{
			Join<?, ?> toJoin = to.join(join.getAttribute().getName(), join.getJoinType());
			toJoin.alias(getAlias(join));
			copyJoins(join, toJoin);
		}
		for (Fetch<?, ?> fetch : from.getFetches()) 
		{
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}
	
	private void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to) 
	{
		for (Fetch<?, ?> fetch : from.getFetches()) 
		{
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}
	
	protected synchronized String getAlias(Selection<?> selection) 
	{
		if (selection != null)
		{
			String alias = selection.getAlias();
			if (alias == null)
			{
				if (aliasCount >= 1000) 
				{
					aliasCount = 0;
				}
				alias = "storeGeneratedAlias" + aliasCount++;
				selection.alias(alias);
			}
			return alias;
		}
		return null;
	}
	
	public void persist(T entity) 
	{
		Assert.notNull(entity);
		entityManager.persist(entity);
	}
	
	public T merge(T entity) {
		Assert.notNull(entity);
		return entityManager.merge(entity);
	}
	
	public void remove(T entity) {
		if (entity != null) {
			entityManager.remove(entity);
		}
	}
	
}
