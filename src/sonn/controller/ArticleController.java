package sonn.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sonn.Order;
import sonn.entity.Article;
import sonn.entity.Comment;
import sonn.entity.User;
import sonn.service.ArticleService;
import sonn.service.CommentService;
import sonn.service.MessageService;
import sonn.service.UserService;
import sonn.util.IOUtils;
import sonn.util.MessageUtils;
import sonn.util.Page;
import sonn.util.PageInfo;
import sonn.util.PageUtils;
import sonn.util.Principal;
import sonn.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ArticleController
 * @Description: ArticleController
 * @author sonne
 * @date 2016-3-25 
 *       2016-05-15 write article func 2016-05-21 save the contents of articles in server context. 
 *       2016.07.30 add links form myspace to write article page and from show article page to ... 
 *       2016.11 article delete function.
 *       2016-11-11 article summary. 
 *       2016-11-13 article edit.
 *       2016-11-27 order or list 
 *       2016-11-28 add date 
 *       2016-12-07 sort the comments. 
 *       2016-12-11 check if the article already exits when write a new article. 
 *       2016-12-23 show messages of usr logined from home page.
 *       2016-12-26 auto log in. 
 *       2016-12-27 check if it is the operation(write,edit,delete articles) of the article's author. 
 *              add the read times.
 *       2016-12-28 - 2017-01.01 fight with xss attack.
 *                     delete all the messages before deleting the article.
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/article")
public class ArticleController {
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;

	@Resource(name = "userServiceImpl")
	private UserService userService;

	@Resource(name = "commentServiceImpl")
	private CommentService commentService;

	@Resource(name = "messageServiceImpl")
	private MessageService messageService;

	/*
	 * Get all articles, and show them at the main page.
	 * 
	 * @param HttpServletRequest request, PageInfo pageInfo, Model model
	 * 
	 * @return the jsp page
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, PageInfo pageInfo,
			Model model) throws Exception {
		// if the usr has setting auto_login, auto login from home page
		userService.check_auto_login(request);

		pageInfo.setEveryPage(20);
		List<Order> orders = new ArrayList<Order>();
		Order order = new Order("id", Order.Direction.desc);
		orders.add(order);
		Page<Article> page = articleService.findPage(pageInfo, Article.class,
				orders);
		List<Article> articleList = page.getContent();
		page.setContent(getArticleListOfSummaryByUrl(articleList));
		model.addAttribute("page", page);
		Principal pipal = userService.getUserPrincipalFromSession(request);
		if (null != pipal) {
			String username = pipal.getUsername();
			model.addAttribute("userName", username);
			if (messageService.hasMsg(userService.find(pipal.getId(),
					User.class)))
				model.addAttribute("has_new_msg", "has_new_msg");
		}
		return "mainPage";
	}

	/*
	 * to show the writing article page.
	 */
	@RequestMapping(value = "/writeArticlePage", method = RequestMethod.GET)
	public String writeArticlePage(HttpServletRequest request,
			PageInfo pageInfo, Model model) throws Exception {
		String username = userService.getUsernameFromSession(request);
		if (StringUtils.isStringEmpty(username))
			return "mainPage";
		model.addAttribute("userName", username);
		return "writeArticlePage";
	}

	/*
	 * @Title: delete
	 * 
	 * @Description: delete article function.
	 * 
	 * @param @param request
	 * 
	 * @param @param id
	 * 
	 * @param @return
	 * 
	 * @param @throws Exception
	 * 
	 * @return boolean
	 * 
	 * @throws
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean delete(HttpServletRequest request, int id) throws Exception {
		String username = userService.getUsernameFromSession(request);
		Article db_article = articleService.find(id, Article.class);
		if (id <= 0 || null == db_article) {
			return false;
		}
		// Check if it is the operation of the article's author.
		if (!db_article.getAuthorName().equals(username)) {
			return false;
		}
		// first,delete all messages of this article.
		messageService.delete_msgs_by_article(db_article);
		articleService.delete(id, Article.class);
		// delete the local files
		IOUtils.delete(db_article.getArticleAddr());
		IOUtils.delete(db_article.getSummaryAddr());
		return true;
	}

	@RequestMapping(value = "/editInit", method = RequestMethod.GET)
	public String editInit(HttpServletRequest request, int id, Model model)
			throws Exception {
		String username = userService.getUsernameFromSession(request);
		if (StringUtils.isStringEmpty(username)) {
			return "mainPage";
		}
		Article article = articleService.find(id, Article.class);
		if (null == articleService.find(id, Article.class)) {
			return "error";
		}
		article = getArticleOfContentByUrl(article);
		model.addAttribute("article", article);
		return "editArticlePage";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject edit(HttpServletRequest request, Article article,
			String articleContent, Model model) throws Exception {
		JSONObject jo = new JSONObject();
		String title_input = article.getTitle();
		if (StringUtils.isStringEmpty(title_input)) {
			jo.put("success", false);
			jo.put("info", "文章标题不能为空");
			return jo;
		}
		if (StringUtils.isStringEmpty(articleContent)) {
			jo.put("success", false);
			jo.put("info", "文章内容不能为空");
			return jo;			
		}
		if (StringUtils.contains_sqlinject_illegal_ch(title_input)) {
			jo.put("success", false);
			jo.put("info", "文章标题不能包含特殊字符['=<>;\"]");
			return jo;			
		}
		articleContent = Jsoup.clean(articleContent, StringUtils.basicWithImages());
		Article db_article = articleService
				.find(article.getId(), Article.class);
		if (db_article == null) {
			jo.put("success", false);
			jo.put("info", "该id文章不存在");
			return jo;
		}
		String username = userService.getUsernameFromSession(request);
		// Check if it is the operation of the article's author.
		if (!db_article.getAuthorName().equals(username)) {
			jo.put("success", false);
			jo.put("info", "你不是这篇文章作者不能修改");
			return jo;
		}
		String old_article_url = db_article.getArticleAddr();
		String old_summary_url = db_article.getSummaryAddr();
		// only the title may be edited on the db
		// and if the title changed content and summary urls will be changed
		// too.
		if (!db_article.getTitle().equals(title_input)) {
			// get new urls
			String articleUrl = articleService.getArticleUrl(article, request,
					username);
			String summaryUrl = articleService.getSummaryUrl(article, request,
					username);
			db_article.setTitle(article.getTitle());
			db_article.setArticleAddr(articleUrl);
			db_article.setSummaryAddr(summaryUrl);
			if (null == db_article.getAuthor()) {
				db_article.setAuthor(userService.findByUserName(username).get(0));
			}
			articleService.update(db_article);
		}

		// history problem, former version donot have a summary
		if (db_article.getSummaryAddr() == null
				|| db_article.getSummaryAddr().equals("")) {
			String summaryUrl = articleService.getSummaryUrl(article, request,
					username);
			db_article.setSummaryAddr(summaryUrl);
			articleService.update(db_article);
		}

		// then update the local file of content and summary
		// first delete
		IOUtils.delete(old_article_url);
		IOUtils.delete(old_summary_url);

		// rewrite content
		IOUtils.writeByUrl(db_article.getArticleAddr(), articleContent);
		// rewrite summary
		// remove all the tags of HTML
		String summary = StringUtils.removeTag(articleContent);
		// get the summary of article
		if (summary.length() > 300) {
			summary = summary.substring(0, 300);
		}
		// write the summary string on the local file
		IOUtils.writeByUrl(db_article.getSummaryAddr(), summary);
		jo.put("success", true);
		jo.put("info", "修改成功");
		return jo;
	}

	@RequestMapping(value = "/writeArticle", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject submit(HttpServletRequest request, Article article,
			String articleContent) throws Exception {
		JSONObject jo = new JSONObject();
		if (null == article || StringUtils.isStringEmpty(articleContent)) {
			jo.put("success", false);
			jo.put("info", "文章内容为空");
			return jo;
		}
		String title_input = article.getTitle();
		if (StringUtils.isStringEmpty(article.getTitle())) {
			jo.put("success", false);
			jo.put("info", "文章题目不可为空");
			return jo;
		}
		if (StringUtils.contains_sqlinject_illegal_ch(title_input)) {
			jo.put("success", false);
			jo.put("info", "文章标题不能包含特殊字符['=<>;\"]");
			return jo;			
		}
		articleContent = Jsoup.clean(articleContent, StringUtils.basicWithImages());
		String username = userService.getUsernameFromSession(request);
		if (StringUtils.isStringEmpty(username)) {
			jo.put("success", false);
			jo.put("msg", "请先登录");
			return jo;				
		}
		
		String articleUrl = articleService.getArticleUrl(article, request,
				username);

		// check if the file already exists
		if (IOUtils.isFileExits(articleUrl)) {
			jo.put("success", false);
			jo.put("info", "您已经写过同样题目文章");
			return jo;
		}

		User user = userService.findByUserName(username).get(0);
		article.setAuthor(user);

		article.setAuthorName(username);
		// 数据库中存储文章路径
		article.setArticleAddr(articleUrl);

		// 向指定目录下存储文章内容
		IOUtils.writeByUrl(articleUrl, articleContent);

		String summaryUrl = articleService.getSummaryUrl(article, request,
				username);
		// remove all the tags of HTML
		String summary = StringUtils.removeTag(articleContent);
		article.setSummaryAddr(summaryUrl);
		// get the summary of article
		if (summary.length() > 300) {
			summary = summary.substring(0, 300);
		}
		// write the summary string on the local file
		IOUtils.writeByUrl(summaryUrl, summary);

		Date date = new Date();
		article.setDate(date);

		articleService.save(article);

		MessageUtils.setSimpleIsSuccessJSON(jo, true);
		return jo;
	}

	/*
	 * Select the article by the id, and show it at the jsp page.
	 * 
	 * @param HttpServletRequest request, Integer id, Model model
	 * 
	 * @return the jsp page
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showFromMainPage(HttpServletRequest request, Integer id,
			Integer currentPage, Model model) throws Exception {
		if (null == id) {
			return "error";
		}
		Article article = articleService.find(id, Article.class);

		// click the link, then read_times ++
		article.setRead_times(article.getRead_times() + 1);
		articleService.update(article);

		article = getArticleOfContentByUrl(article);
		// sort the comments
		List<Comment> comments = commentService.sort(article.getComments());
		String username = userService.getUsernameFromSession(request);
		model.addAttribute("article", article);
		model.addAttribute("username", username);
		model.addAttribute("article_id", id);

		if (currentPage == null || currentPage <= 0) {
			currentPage = 1;
		}
		int totalSize = comments.size();
		PageInfo pageInfo = PageUtils.createPage(10, comments.size(),
				currentPage);
		int beginIndex = pageInfo.getBeginIndex();
		long totalNum = pageInfo.getTotalCount();
		int everyPage = pageInfo.getEveryPage();
		if (totalNum - beginIndex < everyPage) {
			comments = comments.subList(beginIndex, (int) totalNum);
		} else {
			comments = comments.subList(beginIndex, beginIndex + everyPage);
		}

		// 评论分页
		Page<Comment> comments_page = new Page<Comment>(comments, totalSize,
				pageInfo);

		model.addAttribute("comments_page", comments_page);
		return "showArticlePage";
	}

	@RequestMapping(value = "/article_imgs", method = RequestMethod.POST)
	@ResponseBody
	public String article_imgs(HttpServletRequest request) throws Exception {
		String username = userService.getUsernameFromSession(request);
		String fileName = "";

		DiskFileUpload diskFileUpload = new DiskFileUpload();
		try {
			@SuppressWarnings({ "unchecked" })
			List<FileItem> list = diskFileUpload.parseRequest(request);

			for (FileItem fileItem : list) {
				String path = getPathFromSession(username, request);
				fileName = new String(fileItem.getName().getBytes(), "utf-8");
				String localPicPath = path + "/" + fileName;
				File picFile = new File(localPicPath);
				picFile.createNewFile();
				InputStream ins = fileItem.getInputStream();
				OutputStream ous = new FileOutputStream(picFile);
				try {
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = ins.read(buffer)) > -1)
						ous.write(buffer, 0, len);
				} finally {
					ous.close();
					ins.close();
				}
			}
		} catch (FileUploadException e) {
			return "";
		}
		String WEB_PATH = getWebBasePath(username, request) + "article_pics/"
				+ username + "/" + fileName;
		// return the path of picture
		return WEB_PATH;
	}

	private List<Article> getArticleListOfSummaryByUrl(List<Article> articleList) {
		List<Article> newArtiList = new ArrayList<Article>();
		Article article = new Article();
		String url;
		for (int i = 0; i < articleList.size(); i++) {
			article = articleList.get(i);
			if (null == article) {
				break;
			}
			url = article.getSummaryAddr();
			if (StringUtils.isStringEmpty(url)) {
				article.setSummary("");
			} else {
				article.setSummary(IOUtils.readByUrl(url));
			}
			newArtiList.add(article);
		}
		return newArtiList;
	}

	private Article getArticleOfContentByUrl(Article article) {
		article.setContent(IOUtils.readByUrl(article.getArticleAddr()));
		return article;
	}

	private String getPathFromSession(String username,
			HttpServletRequest request) {
		// the path of the user to save the pics
		String basePath = request.getSession().getServletContext()
				.getRealPath("/");
		String path = basePath + "article_pics/" + username;
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		return path;
	}

	private String getWebBasePath(String username, HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}
}