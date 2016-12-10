package sonn.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sonn.entity.Article;
import sonn.service.ArticleService;
import sonn.service.UserService;
import sonn.util.Page;
import sonn.util.PageInfo;


/**
* @ClassName: MySpaceController 
* @Description: personal space
* @author sonne
* @date 2016-5-21 ÏÂÎç6:38:00 
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
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String mySpace(HttpServletRequest request,PageInfo pageInfo,Model model) throws Exception
    {
    	String username = userService.getUsernameFromSession(request);
		pageInfo.setEveryPage(12);
        Page<Article> page = articleService.getArticlesByUsername(username, pageInfo);
       	model.addAttribute("page",page);
       	model.addAttribute("userName",username);
        return "mySpace";
    }
}
