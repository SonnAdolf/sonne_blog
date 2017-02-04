package sonn.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sonn.entity.Article;
import sonn.entity.Comment;
import sonn.entity.Message;
import sonn.enums.MsgIsRead;
import sonn.service.ArticleService;
import sonn.service.CommentService;
import sonn.service.LoginService;
import sonn.service.MessageService;
import sonn.service.UserService;
import sonn.util.IOUtils;
import sonn.util.Page;
import sonn.util.PageInfo;
import sonn.util.PageUtils;
import sonn.util.StringUtils;

/**
* @ClassName: MessageController 
* @Description: tips,message.
* @author sonne
* @date 2016-12
*       2017-01-21 before some operations, check if has logged in first.
*       2017-02-02 set a article's messages readn when this article's link clicked.
* @version 1.0
 */
@Controller
@RequestMapping("/msg")
public class MessageController {
    @Resource(name = "userServiceImpl")
    private UserService userService;
    
    @Resource(name = "messageServiceImpl")
    private MessageService messageService;
    
	@Resource(name = "commentServiceImpl")
	private CommentService commentService;
    
	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpServletRequest request, int article_id,
		                                	Model model) throws Exception {
		Article article = articleService.find(article_id,Article.class);
		List<Message> msgs = messageService.findMsgsByArticle(article);
		for(Message msg:msgs) {
			msg.setIs_read(MsgIsRead.Yes);
		}
		messageService.updates(msgs);
		article = getArticleOfContentByUrl(article);
		// sort the comments
		List<Comment> comments = commentService.sort(article.getComments());
		String username = userService.getUsernameFromSession(request);
		// if has't logged in, turned to login page
		if (StringUtils.isStringEmpty(username)) {
			loginService.loginCommonPretreatment(request, model);
			return "loginPage";
		}
		model.addAttribute("article", article);
		model.addAttribute("username", username);
		model.addAttribute("article_id", article.getId());
		
		int totalSize = comments.size();
		final int CURRENT_PAGE = 1;
		PageInfo pageInfo = PageUtils.createPage(10, comments.size(), CURRENT_PAGE);
		
		int beginIndex = pageInfo.getBeginIndex();
		long totalNum = pageInfo.getTotalCount();
		int everyPage = pageInfo.getEveryPage();
		
		if (totalNum - beginIndex < everyPage) {
			comments = comments.subList(beginIndex, (int) totalNum);
		} else {
			comments = comments.subList(beginIndex, beginIndex + everyPage);
		}

		// ÆÀÂÛ·ÖÒ³
		Page<Comment> comments_page = new Page<Comment>(comments, totalSize,
				pageInfo);

		model.addAttribute("comments_page", comments_page);
		return "showArticlePage";
	}
	
	private Article getArticleOfContentByUrl(Article article) {
		article.setContent(IOUtils.readByUrl(article.getArticleAddr()));
		return article;
	}
}
