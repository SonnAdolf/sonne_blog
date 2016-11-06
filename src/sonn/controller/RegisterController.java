package sonn.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
* @Description: Register CONTROLLER类
* @author 无名
* @date 2016-4-25 下午2:54:41 2016-05-01返回注册提示信息
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
    
    private boolean isContainsChinese(String str)
    {
        String regEx = "[\u4e00-\u9fa5]";
    	Pattern pat = Pattern.compile(regEx);
    	Matcher matcher = pat.matcher(str);
    	boolean flg = false;
    	if (matcher.find())    {
    		flg = true;
    	}
    	return flg;
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
    						false, "Please logout before registering.");
    		return backMessage;    		
    	}
    	if(null == user||StringUtill.isStringEmpty(user.getPassword())
    			||StringUtill.isStringEmpty(user.getUsername()))
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false, "Input Wrong!");
    		return backMessage;
    	}	
    	if(StringUtill.isStringEmpty(repassword))
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false, 
    				              "Please reinput your password!");
    		return backMessage;
    	}
    	if(isContainsChinese(user.getUsername())) 
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false, 
	                "Please use English Name!");
    		return backMessage;    		
    	}
    	List<User> users = userService.findByUserName(user.getUsername());
    	if(!users.isEmpty())
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false, 
    				                "This username has been existing!");
    		return backMessage;
    	}
    	if(!user.getPassword().equals(repassword))
    	{
    		MessageUtil.setSimpleBackMessage(backMessage, false,
    				          "The passwords you inputed are different!");
    		return backMessage;
    	}
    	MessageUtil.setSimpleBackMessage(backMessage, true, 
    			             "You have been the new member of RiXiangBlog!");
    	return backMessage;
    }
}
