package sonn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sonn.Order;
import sonn.entity.Article;
import sonn.entity.User;
import sonn.service.ArticleService;
import sonn.service.LoginService;
import sonn.service.MessageService;
import sonn.service.UserService;
import sonn.util.IOUtils;
import sonn.util.Page;
import sonn.util.PageInfo;
import sonn.util.Principal;
import sonn.util.TimeUtils;

/**
 * @ClassName: MySpaceController
 * @Description: personal space
 * @author sonne
 * @date 2016-5-21 下午6:38:00 
 *       2016-12-13 上传头像功能暂时合入个人主页 
 *       2016-12-14 获取用户注册多久
 *       2017-01-15 if the usr did not log in, return to home page
 *       2017-01-21 before some operations, check if has logged in first.
 *       2017-02-02 articles'order(sorting) setting.
 * @version 1.0
 */
@Controller
@RequestMapping("/space")
public class MySpaceController {
	@Resource(name = "userServiceImpl")
	private UserService userService;

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;

	@Resource(name = "messageServiceImpl")
	private MessageService messageService;

	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String mySpace(HttpServletRequest request, PageInfo pageInfo,
			Model model) throws Exception {
		Principal pipal = userService.getUserPrincipalFromSession(request);
		if (null == pipal) {
		    // if has't logged in, turned to login page
			loginService.loginCommonPretreatment(request, model);
			return "loginPage";
		}
		else{
			if (messageService.hasMsg(userService.find(pipal.getId(),
					User.class)))
				model.addAttribute("has_new_msg", "has_new_msg");
		}

		String username = pipal.getUsername();
		pageInfo.setEveryPage(12);
		List<Order> orders = new ArrayList<Order>();
		Order order = new Order("id", Order.Direction.desc);
		orders.add(order);
		Page<Article> page = articleService.getArticlesByUsername(username,
				pageInfo, orders);
		model.addAttribute("page", page);
		model.addAttribute("userName", username);

		User user = userService.findByUserName(username).get(0);
		// if the user didnot upload his picture,the path is null
		String h_pic_path = null;
		if (null != user.getH_pic_path()) {
			h_pic_path = IOUtils.getRelativePath(user.getH_pic_path());
		} else {
			model.addAttribute("defulat_path", "h_pics/default.jpg");
		}

		model.addAttribute("h_pic_path", h_pic_path);

		// sonne blog age
		int[] arr = TimeUtils.getHowLongFromNow(user.getBlog_date());
		String blog_age = "";
		if (arr[0] != 0) {
			blog_age = blog_age + Integer.toString(arr[0]) + "年";
		}
		if (arr[1] != 0) {
			blog_age = blog_age + Integer.toString(arr[1]) + "个月";
		}
		// if he's new user
		if (arr[0] == 0 && arr[1] == 0) {
			blog_age = "不足一个月";
		}
		
		model.addAttribute("blog_age", blog_age);
		return "mySpace";
	}

	@RequestMapping(value = "/other_space", method = RequestMethod.GET)
	public String other_space(HttpServletRequest request, PageInfo pageInfo,
			String usr_name, Model model) throws Exception {
		Principal pipal = userService.getUserPrincipalFromSession(request);
		if (null != pipal) {
			model.addAttribute("my_name", pipal.getUsername());
			if (messageService.hasMsg(userService.find(pipal.getId(),
					User.class)))
				model.addAttribute("has_new_msg", "has_new_msg");
		}
		
		model.addAttribute("userName", usr_name);

		pageInfo.setEveryPage(12);
		List<Order> orders = new ArrayList<Order>();
		Order order = new Order("id", Order.Direction.desc);
		orders.add(order);
		Page<Article> page = articleService.getArticlesByUsername(usr_name,
				pageInfo, orders);
		model.addAttribute("page", page);

		User user = userService.findByUserName(usr_name).get(0);
		// if the user didnot upload his picture,the path is null
		String h_pic_path = null;
		if (null != user.getH_pic_path()) {
			h_pic_path = IOUtils.getRelativePath(user.getH_pic_path());
		} else {
			model.addAttribute("defulat_path", "h_pics/default.jpg");
		}

		model.addAttribute("h_pic_path", h_pic_path);

		// sonne blog age
		int[] arr = TimeUtils.getHowLongFromNow(user.getBlog_date());
		String blog_age = "";
		if (arr[0] != 0) {
			blog_age = blog_age + Integer.toString(arr[0]) + "年";
		}
		if (arr[1] != 0) {
			blog_age = blog_age + Integer.toString(arr[1]) + "个月";
		}
		// if he's new user
		if (arr[0] == 0 && arr[1] == 0) {
			blog_age = "不足一个月";
		}

		model.addAttribute("blog_age", blog_age);
		return "other_space";
	}
}
