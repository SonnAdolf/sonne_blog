package sonn.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sonn.entity.Article;
import sonn.entity.Comment;
import sonn.entity.Message;
import sonn.enums.MsgIsRead;
import sonn.enums.MsgType;
import sonn.service.ArticleService;
import sonn.service.CommentService;
import sonn.service.MessageService;
import sonn.service.UserService;
import sonn.util.Principal;
import sonn.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: CommentController
 * @Description: controller of comments
 * @author sonne
 * @date 2016-12-5 20:41:07
 * @version 1.0
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;

	@Resource(name = "commentServiceImpl")
	private CommentService commentService;

	@Resource(name = "userServiceImpl")
	private UserService userService;

	@Resource(name = "messageServiceImpl")
	private MessageService messageService;

	@RequestMapping(value = "/writeComment", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject submit(HttpServletRequest request, Comment comment,
			int article_id) throws Exception {
		JSONObject json = new JSONObject();
		Principal principal = userService.getUserPrincipalFromSession(request);
		if( null == principal) {
			json.put("success", false);
			json.put("msg", "请先登录");
			return json;			
		}
		String content = comment.getContent();
		if (StringUtils.isStringEmpty(content.trim())) {
			json.put("success", false);
			json.put("msg", "评论内容怎可为空白？(╯#-_-)╯~~~~~~~~~~~~~~~~~╧═╧");
			return json;
		}
		if (content.length() >= 400) {
			json.put("success", false);
			json.put("msg", "评论个四百字也就够了。(¬､¬) (￢_￢)");
			return json;
		}
		content = StringUtils.replace_html_tags(content);
		comment.setContent(content);
		Article article = articleService.find(article_id, Article.class);
		comment.setArticle(article);
		comment.setDate(new Date());
		String username = userService.getUsernameFromSession(request);
		if (username != null) {
			comment.setAuthorName(username);
		} else {
			comment.setAuthorName("a visitor");
		}
		commentService.save(comment);
		Message msg = new Message();
		msg.setType(MsgType.Comment);
		// old version Article entity class is without Author class
		if (article.getAuthor() != null) {
			msg.setReciever(article.getAuthor());
		}
		if (username != null) {
			msg.setSender(userService.findByUserName(username).get(0));
		}
		msg.setIs_read(MsgIsRead.No);
		msg.setDate(new Date());
		msg.setArticle(article);
		messageService.save(msg);
		json.put("success", true);
		json.put("msg", "评论成功 ♪（＾∀＾●）ﾉｼ （●´∀｀）♪");
		return json;
	}
}
