package com.serviceimpl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.dao.ArticleDao;
import com.entity.Article;
import com.service.ArticleService;
import com.util.Page;
import com.util.PageInfo;
import com.util.Principal;

/**
 * @author 无名
 * @date 2016.04.21
 * @description:文章service实现类
 */
@Service("articleServiceImpl")
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService
{
	
	@Resource(name = "articleDaoImpl")
	private ArticleDao articleDao;
	
	@Resource(name = "articleDaoImpl")
	public void setBaseDao(ArticleDao articleDao)
	{
		super.setBaseDao(articleDao);
	}

	@Override
	public List<Article> findAllArticle() 
	{
		return articleDao.findAllArticles();
	}
	
	/*
	 * 返回article该存储的路径   
	 */
	@Override
	public String getArticleUrl(Article article,HttpServletRequest request,Principal userPrincipal)
	{
		String basePath = "d:\\apache-tomcat-7.0.52\\article\\";
		String articleUrl = basePath + userPrincipal.getUsername()+"\\"
		                                    + article.getTitle() + ".txt";
		return articleUrl;
	}

	/*
	 * 根据用户名查找文章
	 */
	@Override
	public Page<Article> getArticlesByUsername(String username,PageInfo pageInfo) 
	{
		return articleDao.getArticlesByUsername(username,pageInfo);
	}

}
