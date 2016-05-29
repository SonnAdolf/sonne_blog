package com.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.entity.User;
import com.message.bean.SimpleBackMessage;
import com.service.UserService;
import com.util.MessageUtil;
import com.util.StringUtill;

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
    
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject submit(User user,String repassword) throws Exception
    {
		JSONObject jo = new JSONObject();	
    	SimpleBackMessage registerMessage = checkUserInfor(user,repassword);
    	MessageUtil.setJSONObject(jo,registerMessage);
    	if(!registerMessage.isSuccess())
    	{
            return jo;
    	}
		userService.save(user);
        return jo;
    }
    
    private SimpleBackMessage checkUserInfor(User user,String repassword)
    {
    	SimpleBackMessage backMessage = new SimpleBackMessage();
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
