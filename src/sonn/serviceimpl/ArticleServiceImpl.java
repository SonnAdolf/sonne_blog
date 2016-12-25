package sonn.serviceimpl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import sonn.dao.ArticleDao;
import sonn.entity.Article;
import sonn.service.ArticleService;
import sonn.util.Page;
import sonn.util.PageInfo;

/**
 * @author sonne
 * @date 2016.04.21 2016-11-11 article summary 2016-11-27 order
 * @description:文章service实现类
 */
@Service("articleServiceImpl")
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements
		ArticleService {

	@Resource(name = "articleDaoImpl")
	private ArticleDao articleDao;

	@Resource(name = "articleDaoImpl")
	public void setBaseDao(ArticleDao articleDao) {
		super.setBaseDao(articleDao);
	}

	@Override
	public List<Article> findAllArticle() {
		return articleDao.findAllArticles();
	}

	/*
	 * 返回article该存储的路径
	 */
	@Override
	public String getArticleUrl(Article article, HttpServletRequest request,
			String username) {
		String basePath = "d:\\apache-tomcat-7.0.52\\article\\";
		String articleUrl = basePath + username + "\\" + article.getTitle()
				+ ".txt";
		return articleUrl;
	}

	/*
	 * get the url of the summary
	 */
	@Override
	public String getSummaryUrl(Article article, HttpServletRequest request,
			String username) {
		String basePath = "d:\\apache-tomcat-7.0.52\\summary\\";
		String summaryUrl = basePath + username + "\\" + article.getTitle()
				+ ".txt";
		return summaryUrl;
	}

	/*
	 * 根据用户名查找文章
	 */
	@Override
	public Page<Article> getArticlesByUsername(String username,
			PageInfo pageInfo) {
		return articleDao.getArticlesByUsername(username, pageInfo);
	}

}
