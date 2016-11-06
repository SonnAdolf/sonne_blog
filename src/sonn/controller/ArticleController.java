package sonn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sonn.entity.Article;
import sonn.entity.User;
import sonn.service.ArticleService;
import sonn.util.IOUtill;
import sonn.util.MessageUtil;
import sonn.util.Page;
import sonn.util.PageInfo;
import sonn.util.Principal;
import sonn.util.StringUtill;

import com.alibaba.fastjson.JSONObject;

/**
* @ClassName: ArticleController 
* @Description: ArticleController
* @author sonn
* @date 2016-3-25 2016-05-15 write article func 
*       2016-05-21 save the contents of articles in server context.
*       2016.07.30 add links form myspace to write article page
*                        and from show article page to ...
* @version 1.0
 */
@Controller
@RequestMapping("/article")
public class ArticleController 
{
    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;
    
    /**
     * Get all articles, and show them at the main page.
     *
     * @param  HttpServletRequest request, PageInfo pageInfo, Model model
     * @return the jsp page
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request,PageInfo pageInfo,
    		                   Model model) throws Exception
    {
    	pageInfo.setEveryPage(6);
    	Page<Article> page = articleService.findPage(pageInfo,Article.class);
    	List<Article> articleList = page.getContent();
    	page.setContent(getArticleListOfContentByUrl(articleList));
    	model.addAttribute("page",page);
    	
		HttpSession session = request.getSession();
		//get login user
		Principal userPrincipal =
				(Principal) session. getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		if(null != userPrincipal)
		{
			String userName = userPrincipal.getUsername();
	    	model.addAttribute("userName",userName);
		}
        return "mainPage";
    }
    
    @RequestMapping(value = "/writeArticlePage", method = RequestMethod.GET)
    public String writeArticlePage(HttpServletRequest request,PageInfo pageInfo,Model model) throws Exception
    {
		HttpSession session = request.getSession();
		//获取登录用户
		Principal userPrincipal =
				(Principal) session. getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		String userName = userPrincipal.getUsername();
       	model.addAttribute("userName",userName);
        return "writeArticlePage";
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public boolean delete(HttpServletRequest request,int id) throws Exception
    {
		JSONObject jo = new JSONObject();
		if(id <= 0 || null == articleService.find(id, Article.class)) 
		{
			return false;
		}
		articleService.delete(id, Article.class);
		MessageUtil.setSimpleIsSuccessJSON(jo, true);
        return true;
    }
    
    @RequestMapping(value = "/writeArticle", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject submit(HttpServletRequest request,Article article,
    		                             String articleContent) throws Exception
    {
		JSONObject jo = new JSONObject();
		if(null == article || StringUtill.isStringEmpty(articleContent))
		{
			MessageUtil.setSimpleIsSuccessJSON(jo, false);
	        return jo;
		}
		HttpSession session = request.getSession();
		Principal userPrincipal =
				(Principal) session. getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		article.setAuthorName(userPrincipal.getUsername());

		String articleUrl = 
				articleService.getArticleUrl(article, request, userPrincipal);

		//数据库中存储文章路径
		article.setArticleAddr(articleUrl);
		
		//向指定目录下存储文章内容
		IOUtill.writeByUrl(articleUrl,articleContent);
		
		articleService.save(article);
		
		MessageUtil.setSimpleIsSuccessJSON(jo, true);
        return jo;
    }
    
    /**
     * Select the article by the id, and show it at the jsp page.
     *
     * @param  HttpServletRequest request, Integer id, Model model
     * @return the jsp page
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(HttpServletRequest request, Integer id, Model model) throws Exception
    {
    	if(null == id)
    	{
    		return "error";
    	}
		Article article = articleService.find(id,Article.class);
		article = getArticleOfContentByUrl(article);
		
		HttpSession session = request.getSession();
		//get login user
		Principal userPrincipal =
				(Principal) session. getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		String userName = userPrincipal.getUsername();
		
       	model.addAttribute("userName",userName);
    	model.addAttribute("article",article);
        return "showArticlePage";
    }
    
    /*
     * 数据库里保存的是文章路径，将从数据库里查询到的文章list（只有文章路径，不含文章内容）
     * 转化为包含文章内容的文章list（依据路径读取文章内容）
     */
    private List<Article> getArticleListOfContentByUrl(List<Article> articleList)
    {
    	List<Article> newArtiList = new ArrayList<Article>();
    	Article article = new Article();
    	String url;
    	for(int i = 0; i < articleList.size(); i++ )
    	{
    		article = articleList.get(i);
    		if(null == article)
    		{
    			break;
    		}
    		url = article.getArticleAddr();
    		if(StringUtill.isStringEmpty(url))
    		{
    			article.setContent("");
    		}
    		else
    		{
        		article.setContent(IOUtill.readByUrl(url));
    		}
    		newArtiList.add(article);
    	}
    	return newArtiList;
    }
    
    private Article getArticleOfContentByUrl(Article article)
    {
    	article.setContent(IOUtill.readByUrl(article.getArticleAddr()));
    	return article;
    }
    
}