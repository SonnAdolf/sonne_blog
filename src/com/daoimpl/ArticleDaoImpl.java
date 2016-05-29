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

import com.dao.ArticleDao;
import com.entity.Article;
import com.util.Page;
import com.util.PageInfo;
import com.util.StringUtill;

/**
 * @author 无名
 * @date 2016.04.21
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
		if (!StringUtill.isStringEmpty(username)) 
		{
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("authorName"), username));
		}
		criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageInfo,Article.class);
	}
	
}
