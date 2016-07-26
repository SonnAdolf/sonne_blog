package sonn.dao;

import java.util.List;

import sonn.entity.Article;
import sonn.util.Page;
import sonn.util.PageInfo;


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
