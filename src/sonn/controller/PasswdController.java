package sonn.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sonn.entity.User;
import sonn.service.UserService;
import sonn.util.StringUtill;

import com.alibaba.fastjson.JSONObject;

/**
* @ClassName: PasswdController 
* @Description: passwd
* @author sonne
* @date 2016-11-27 15:32:34 
* @version 1.0
 */
@Controller
@RequestMapping("/passwd")
public class PasswdController 
{
    @Resource(name = "userServiceImpl")
    private UserService userService;
    
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show()throws Exception
    {
        return "changePasswdPage";
    }
    
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject change(HttpServletRequest request, String password,
    		             String newPassword, String rePassword) throws Exception
    {
		JSONObject jo = new JSONObject();
		if (StringUtill.isStringEmpty(password) 
				|| StringUtill.isStringEmpty(newPassword)
				|| StringUtill.isStringEmpty(rePassword))
		{
			jo.put("success", false);
			jo.put("returnMessage", "输入值不允许为空..@_@|||||..");
			return jo;
		}	
		if (newPassword.equals(password))
		{
			jo.put("success", false);
			jo.put("returnMessage", "输入的新密码没有更改..@_@|||||..");
			return jo;				
		}
		if (!newPassword.equals(rePassword))
		{
			jo.put("success", false);
			jo.put("returnMessage", "两次输入密码不一致..@_@|||||..");
			return jo;			
		}
    	if(!userService.validPwd(newPassword))
    	{
			jo.put("success", false);
			jo.put("returnMessage",
			   "你需要一个更复杂的密码 (至少六位，包含字母，数字，特殊字符，且必须以字母开头)..@_@|||||..");
			return jo;  		
    	}
    	String username = userService.getUsernameFromSession(request);
		User user = userService.findByUserName(username).get(0);
		// for compatible with the old version, here md5 or not
		if (!user.getPassword().equals(DigestUtils.md5Hex(password)) 
				&& !user.getPassword().equals(password)) 
		{
			jo.put("success", false);
			jo.put("returnMessage", "密码输入错误>_<||| ");
			return jo;				
		}
		// md5 hex
		user.setPassword(DigestUtils.md5Hex(newPassword));
		userService.update(user);
		jo.put("success", true);
		jo.put("returnMessage", "‘（*∩_∩*）′");
        return jo;
    }
    
}
