package sonn.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sonn.entity.User;
import sonn.service.UserService;
import sonn.util.PageInfo;

@Controller
@RequestMapping("/mine")
public class MineController {
	
    @Resource(name = "userServiceImpl")
    private UserService userService;
    
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpServletRequest request, PageInfo pageInfo,
			Model model) throws Exception {
		String username = userService.getUsernameFromSession(request);
		User user = userService.findByUserName(username).get(0);
		// if the user didnot upload his picture,the path is null
		String h_pic_path = null;
		if (null != user.getH_pic_path()) {
			h_pic_path = getRelativePath(user.getH_pic_path());
		}
		else {
			model.addAttribute("defulat_path", "h_pics/default.jpg");
		}
		model.addAttribute("h_pic_path", h_pic_path);
		return "minePage";
	}
	
	private String getRelativePath(String path) {
		String[] str_arr = path.split("/");
		int len = str_arr.length;
		String relativePath = str_arr[len-3] + "/" + str_arr[len-2] + "/" + str_arr[len-1];
		return relativePath;
	}
}


