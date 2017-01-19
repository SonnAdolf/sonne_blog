package sonn.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sonn.entity.User;

/**
* @ClassName: LogoutController 
* @Description: Usr log out.
* @author Sonne
* @date 2017-1-18 17:17:42 
* @version 1.0
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {
	@RequestMapping(value = "/lo", method = RequestMethod.POST)
	@ResponseBody
	public boolean submit(HttpServletRequest request,
			HttpServletResponse response, User user, String auto_log_in,
			String captcha) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("SONNE_BLOG_LOG_IN_USRNAME".equals(cookie.getName())) {
					Cookie newCookie=new Cookie("SONNE_BLOG_LOG_IN_USRNAME",null);
					newCookie.setMaxAge(0); 
					newCookie.setPath("/"); 
					response.addCookie(newCookie); 
				}
				if ("SONNE_BLOG_LOG_IN_PASSWD".equals(cookie.getName())) {
					Cookie newCookie=new Cookie("SONNE_BLOG_LOG_IN_PASSWD",null);
					newCookie.setMaxAge(0); 
					newCookie.setPath("/"); 
					response.addCookie(newCookie); 
				}
			}
		}
		return true;
	}
}
