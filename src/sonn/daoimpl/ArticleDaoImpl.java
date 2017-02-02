package sonn.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import sonn.Order;
import sonn.dao.ArticleDao;
import sonn.entity.Article;
import sonn.util.Page;
import sonn.util.PageInfo;
import sonn.util.StringUtils;


/**
 * @author sonne
 * @date 2016.04.21
 *       2017-02-02 articles'order(sorting) setting.
 * @description:文章dao实现类
 */
@Repository("articleDaoImpl")
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao 
{
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public List<Article> findAllArticles() 
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		Root<Article> root = criteriaQuery.from(Article.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

	/*
	 * 根据用户名查找文章
	 */
	@Override
	public Page<Article> getArticlesByUsername(String username,PageInfo pageInfo) 
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		Root<Article> root = criteriaQuery.from(Article.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (!StringUtils.isStringEmpty(username)) 
		{
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("authorName"), username));
		}
		criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageInfo,Article.class);
	}
	
	/*
	 * 根据用户名查找文章
	 */
	@Override
	public Page<Article> getArticlesByUsername(String username, PageInfo pageInfo, List<Order> orders) 
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		Root<Article> root = criteriaQuery.from(Article.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (!StringUtils.isStringEmpty(username)) 
		{
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("authorName"), username));
		}
		criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageInfo,Article.class,orders);
	}
	
}
