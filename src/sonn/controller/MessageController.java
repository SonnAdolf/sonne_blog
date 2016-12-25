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
import sonn.service.CommentService;
import sonn.service.MessageService;
import sonn.service.UserService;
import sonn.util.IOUtill;
import sonn.util.Page;
import sonn.util.PageInfo;
import sonn.util.PageUtil;

@Controller
@RequestMapping("/msg")
public class MessageController {
    @Resource(name = "userServiceImpl")
    private UserService userService;
    
    @Resource(name = "messageServiceImpl")
    private MessageService messageService;
    
	@Resource(name = "commentServiceImpl")
	private CommentService commentService;
    
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpServletRequest request, int id,
		                                	Model model) throws Exception {
		Message msg = messageService.find(id, Message.class);
		msg.setIs_read(MsgIsRead.Yes);
		messageService.update(msg);
		Article article = msg.getArticle();
		article = getArticleOfContentByUrl(article);
		// sort the comments
		List<Comment> comments = commentService.sort(article.getComments());
		String username = userService.getUsernameFromSession(request);
		
		model.addAttribute("article", article);
		model.addAttribute("userName", username);
		model.addAttribute("article_id", id);
		
		int totalSize = comments.size();
		final int CURRENT_PAGE = 1;
		PageInfo pageInfo = PageUtil.createPage(10, comments.size(), CURRENT_PAGE);
		
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
		article.setContent(IOUtill.readByUrl(article.getArticleAddr()));
		return article;
	}
}
