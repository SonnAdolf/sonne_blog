package sonn.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sonn.entity.Article;
import sonn.entity.User;
import sonn.service.ArticleService;
import sonn.service.MessageService;
import sonn.service.UserService;
import sonn.util.IOUtill;
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
* @version 1.0
 */
@Controller
@RequestMapping("/space")
public class MySpaceController 
{
    @Resource(name = "userServiceImpl")
    private UserService userService;
    
    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;
    
    @Resource(name = "messageServiceImpl")
    private MessageService messageService;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String mySpace(HttpServletRequest request,PageInfo pageInfo,Model model) throws Exception
    {
		Principal pipal = userService.getUserPrincipalFromSession(request);
		String username = pipal.getUsername();
		if (null != pipal) {
			model.addAttribute("userName", username);
			if (messageService.hasMsg(userService.find(pipal.getId(),User.class)))
				model.addAttribute("has_new_msg", "has_new_msg");	
		}
		
		pageInfo.setEveryPage(12);
        Page<Article> page = articleService.getArticlesByUsername(username, pageInfo);
       	model.addAttribute("page",page);
       	model.addAttribute("userName",username);
       	
		User user = userService.findByUserName(username).get(0);
		// if the user didnot upload his picture,the path is null
		String h_pic_path = null;
		if (null != user.getH_pic_path()) {
			h_pic_path = IOUtill.getRelativePath(user.getH_pic_path());
		}
		else {
			model.addAttribute("defulat_path", "h_pics/default.jpg");
		}
		
		model.addAttribute("h_pic_path", h_pic_path);
		model.addAttribute("username", user.getUsername());
		
		// 获取用户注册多久
		int[] arr = TimeUtils.getHowLongFromNow(user.getBlog_date());
		String blog_age = "";
		if (arr[0] != 0) {
			blog_age = blog_age + Integer.toString(arr[0]) + "年";
		}
		if (arr[1] != 0) {
			blog_age = blog_age + Integer.toString(arr[1]) + "个月";
		}
		
		model.addAttribute("blog_age", blog_age);      	
        return "mySpace";
    }
}
