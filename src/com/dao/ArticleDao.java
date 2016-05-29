package com.dao;

import java.util.List;

import com.entity.Article;
import com.util.Page;
import com.util.PageInfo;

/**
 * @author 无名
 * @date 2016.04.21 
 * @description:文章dao接口
 */
public interface ArticleDao extends BaseDao<Article>
{
	public List<Article> findAllArticles();
	
	/*
	 * 根据用户名查找文章
	 */
	public Page<Article> getArticlesByUsername(String username,PageInfo pageInfo);
}
