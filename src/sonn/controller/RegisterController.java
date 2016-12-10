package sonn.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sonn.entity.User;
import sonn.message.bean.SimpleBackMessage;
import sonn.service.UserService;
import sonn.util.MessageUtil;
import sonn.util.Principal;
import sonn.util.StringUtill;

import com.alibaba.fastjson.JSONObject;

/**
* @ClassName: RegisterController 
* @Description: Register CONTROLLER
* @author sonne
* @date 2016-4-25 下午2:54:41 2016-05-01返回注册提示信息
*       2016-11-27  check passwd's complexity
* @version 1.0
 */
@Controller
@RequestMapping("/register")
public class RegisterController
{
	
    @Resource(name = "userServiceImpl")
    private UserService userService;
    
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show()throws Exception
    {
        return "registerPage";
    }
    
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject submit(HttpServletRequest request,
    		User user,String repassword) throws Exception
    {
    	HttpSession session = request.getSession();
		JSONObject jo = new JSONObject();	
    	SimpleBackMessage registerMessage 
    					= checkUserInfor(user,repassword,session);
    	MessageUtil.setJSONObject(jo,registerMessage);
    	if(!registerMessage.isSuccess())
    	{
            return jo;
    	}
    	// using md5 to set the passwd
    	user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		userService.save(user);
    	session.setAttribute(User.PRINCIPAL_ATTRIBUTE_NAME,
	              new Principal(user.getId(),user.getUsername()));
        return jo;
    }
    
    private SimpleBackMessage checkUserInfor(User user,
    					String repassword,HttpSession session)
    {
    	SimpleBackMessage backMessage = new SimpleBackMessage();
    	Object sessionMsg = session.getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
    	if(null != sessionMsg) {
    		MessageUtil.setSimpleBackMessage(backMessage,
    						false, "注册前请先退出.( ^_^ )? ");
    		return backMessage;    		
    	}
    	if(null == user||StringUtill.isStringEmpty(user.getPassword())
    			||StringUtill.isStringEmpty(user.getUsername()))
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false, "输入有误!( ^_^ )? ");
    		return backMessage;
    	}	
    	if(!userService.validPwd(user.getPassword()))
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false, "密码至少六位(╯#-_-)╯~~~~~~~~~~~~~~~~~╧═╧  ");
    		return backMessage;   		
    	}
    	if(StringUtill.isStringEmpty(repassword))
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false, 
    				              "请再次输入你的密码( ^_^ )? !");
    		return backMessage;
    	}
    	if(StringUtill.isContainsChinese(user.getUsername())) 
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false, 
	                "请使用英文名!..@_@|||||..");
    		return backMessage;    		
    	}
    	List<User> users = userService.findByUserName(user.getUsername());
    	if(!users.isEmpty())
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false, 
    				                "该名称已被使用!..@_@|||||..");
    		return backMessage;
    	}
    	if(!user.getPassword().equals(repassword))
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false,
    				          "两次输入密码相同!..@_@|||||..");
    		return backMessage;
    	}
    	MessageUtil.setSimpleBackMessage(backMessage, true, 
    			             "恭喜成为日向博客新成员!(^_^)∠※ 送你一束花。");
    	return backMessage;
    }
 
}
