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
* @author sonne
* @date 2016-3-25 2016-05-15 write article func 
*       2016-05-21 save the contents of articles in server context.
*       2016.07.30 add links form myspace to write article page
*                        and from show article page to ...
*       2016.11   article delete function.
*       2016-11-11 article summary
*       2016-11-13 article edit
* @version 1.0
 */
@Controller
@RequestMapping("/article")
public class ArticleController 
{
    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;
    
    /*
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
    	page.setContent(getArticleListOfSummaryByUrl(articleList));
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
		//get the user who logins
		Principal userPrincipal =
				(Principal) session. getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		String userName = userPrincipal.getUsername();
       	model.addAttribute("userName",userName);
        return "writeArticlePage";
    }
    
    /*
    * @Title: delete 
    * @Description: delete article function.
    * @param @param request
    * @param @param id
    * @param @return
    * @param @throws Exception    
    * @return boolean  
    * @throws
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public boolean delete(HttpServletRequest request,int id) throws Exception
    {
		Article db_article = articleService.find(id, Article.class);
		if(id <= 0 || null == db_article) 
		{
			return false;
		}
		articleService.delete(id, Article.class);
		//delete the local files
		IOUtill.delete(db_article.getArticleAddr());
		IOUtill.delete(db_article.getSummaryAddr());
        return true;
    }
    
    @RequestMapping(value = "/editInit", method = RequestMethod.GET)
    public String editInit(HttpServletRequest request,Integer id, Model model) throws Exception
    {
    	if(null == id)
    	{
    		return "error";
    	}
		Article article =  articleService.find(id, Article.class);
		if(null == articleService.find(id, Article.class)) 
		{
			return "error";
		}
		article = getArticleOfContentByUrl(article);
    	model.addAttribute("article",article);
        return "editArticlePage";
    }
    

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public boolean edit(HttpServletRequest request,Article article, String articleContent, Model model) throws Exception
    {
    	String c = request.getParameter("articleContent");
		Article db_article =  articleService.find(article.getId(), Article.class);
		if (db_article == null) 
		{
			return false;
		}
		
		// only the title may be edited on the db 
		// and if the title changed content and summary urls will be changed too.
		if (!db_article.getTitle().equals(article.getTitle()))
		{
			HttpSession session = request.getSession();
			Principal userPrincipal =
					(Principal) session. getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
			//get new urls
			String articleUrl = 
					articleService.getArticleUrl(article, request, userPrincipal);	
			String summaryUrl = 
					articleService.getSummaryUrl(article, request, userPrincipal);
			article.setArticleAddr(articleUrl);
			article.setSummaryAddr(summaryUrl);
			articleService.update(article);
		}
		
		// history problem, former version donot have a summary 
		if (db_article.getSummaryAddr() == null || db_article.getSummaryAddr().equals(""))
		{
			HttpSession session = request.getSession();
			Principal userPrincipal =
					(Principal) session. getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);	
			String summaryUrl = 
					articleService.getSummaryUrl(article, request, userPrincipal);	
			article.setSummaryAddr(summaryUrl);
			articleService.update(article);
		}
		
		// then update the local file of content and summary
		// first delete 
		IOUtill.delete(db_article.getArticleAddr());
		IOUtill.delete(db_article.getSummaryAddr());
		
		// rewrite content
		IOUtill.writeByUrl(article.getArticleAddr(), articleContent);
		// rewrite summary
		// remove all the tags of HTML
		String summary = StringUtill.removeTag(articleContent);
		//get the summary of article
		if(summary.length() > 300) 
		{
			summary = summary.substring(0, 300);
		}
		//write the summary string on the local file
		IOUtill.writeByUrl(article.getSummaryAddr(), summary);
		
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
		
		String summaryUrl = 
				articleService.getSummaryUrl(article, request, userPrincipal);
		//remove all the tags of HTML
		String summary = StringUtill.removeTag(articleContent);
		article.setSummaryAddr(summaryUrl);
		//get the summary of article
		if(summary.length() > 300) 
		{
			summary = summary.substring(0, 300);
		}
		//write the summary string on the local file
		IOUtill.writeByUrl(summaryUrl, summary);
		
		articleService.save(article);
		
		MessageUtil.setSimpleIsSuccessJSON(jo, true);
        return jo;
    }
    
    /*
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
     * show a article's content by clicking the url from main page.
     *
     * @param  HttpServletRequest request, Integer id, Model model
     * @return the jsp page
     */
    @RequestMapping(value = "/showFromMainPage", method = RequestMethod.GET)
    public String showFromMainPage(HttpServletRequest request, Integer id, Model model) throws Exception
    {
    	if(null == id)
    	{
    		return "error";
    	}
		Article article = articleService.find(id,Article.class);
		article = getArticleOfContentByUrl(article);
		
    	model.addAttribute("article",article);
        return "showArticlePage";
    }
    

    private List<Article> getArticleListOfSummaryByUrl(List<Article> articleList)
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
    		url = article.getSummaryAddr();
    		if(StringUtill.isStringEmpty(url))
    		{
    			article.setSummary("");
    		}
    		else
    		{
        		article.setSummary(IOUtill.readByUrl(url));
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