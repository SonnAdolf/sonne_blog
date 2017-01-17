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
import sonn.entity.Message;
import sonn.entity.User;
import sonn.service.MessageService;
import sonn.service.UserService;
import sonn.util.IOUtils;
import sonn.util.Page;
import sonn.util.PageInfo;
import sonn.util.Principal;
import sonn.util.TimeUtils;

@Controller
@RequestMapping("/mine")
public class MineController {
	
    @Resource(name = "userServiceImpl")
    private UserService userService;
    
    @Resource(name = "messageServiceImpl")
    private MessageService messageService;
    
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpServletRequest request, PageInfo pageInfo,
		                                	Model model) throws Exception {
		Principal pipal = userService.getUserPrincipalFromSession(request);
		// if the usr should relogin,just back to the home page.
		if (null == pipal)
			return "mainPage";
		String username = pipal.getUsername();
		User user = userService.findByUserName(username).get(0);
		// if the user didnot upload his picture,the path is null
		String h_pic_path = null;
		if (null != user.getH_pic_path()) {
			h_pic_path = IOUtils.getRelativePath(user.getH_pic_path());
		}
		else {
			model.addAttribute("defulat_path", "h_pics/default.jpg");
		}
		model.addAttribute("h_pic_path", h_pic_path);
		model.addAttribute("userName", username);
		
		pageInfo.setEveryPage(12);
		List<Order> orders = new ArrayList<Order>();
		Order order = new Order("id", Order.Direction.desc);
		orders.add(order);
		Page<Message> msgPage = 
					messageService.getMessagesByUsrId(user, orders, pageInfo);
		model.addAttribute("msgPage", msgPage);	
		
		model.addAttribute("username", user.getUsername());
		
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
		
		return "minePage";
	}
}


