package sonn.controller;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sonn.entity.User;
import sonn.message.bean.SimpleBackMessage;
import sonn.service.UserService;
import sonn.util.MessageUtil;
import sonn.util.Principal;
import sonn.util.RSAUtils;
import sonn.util.StringUtill;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: RegisterController
 * @Description: Register CONTROLLER
 * @author sonne
 * @date 2016-4-25 下午2:54:41 2016-05-01返回注册提示信息 2016-11-27 check passwd's
 *       complexity 2016-12-20 Password Encryption
 * @version 1.0
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

	@Resource(name = "userServiceImpl")
	private UserService userService;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpServletRequest request, Model model)
			throws Exception {
		HttpSession session = request.getSession();
		// rsa key pair
		Map<String, Object> map = RSAUtils.genKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) map.get("RSAPublicKey");
		RSAPrivateKey privateKey = (RSAPrivateKey) map.get("RSAPrivateKey");
		String strPublicKey = userService.getKeyString(publicKey);
		String strPrivateKey = userService.getKeyString(privateKey);
		// public key send to client
		model.addAttribute("publicKey", strPublicKey);
		// private key save in session
		session.setAttribute("PRIVATE_KEY", strPrivateKey);
		return "registerPage";
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject submit(HttpServletRequest request, User user,
			String repassword) throws Exception {
		HttpSession session = request.getSession();
		JSONObject jo = new JSONObject();
//		jo.put("success", false);
//		jo.put("returnMessage", "本博客目前处于开发测试时期，2016年1月将暂停注册功能");
//		return jo;
		SimpleBackMessage registerMessage = checkUserInfor(user, repassword,
	    			session);
		MessageUtil.setJSONObject(jo, registerMessage);
		if (!registerMessage.isSuccess()) {
			return jo;
		}
		// get private key from session
		String PRIVATE_KSY = (String) session.getAttribute("PRIVATE_KEY");
		String passwd = RSAUtils.decryptDataOnJava(user.getPassword(),
				PRIVATE_KSY);
		// using md5 to set the passwd
		user.setPassword(DigestUtils.md5Hex(passwd));
		user.setBlog_date(new Date());
		userService.save(user);
		session.setAttribute(User.PRINCIPAL_ATTRIBUTE_NAME,
				new Principal(user.getId(), user.getUsername()));
		return jo;
	}

	private SimpleBackMessage checkUserInfor(User user, String repassword,
			HttpSession session) {
		SimpleBackMessage backMessage = new SimpleBackMessage();
		Object sessionMsg = session.getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		if (null != sessionMsg) {
			MessageUtil.setSimpleBackMessage(backMessage, false,
					"注册前请先退出.( ^_^ )? ");
			return backMessage;
		}

		if (null == user || StringUtill.isStringEmpty(user.getPassword())
				|| StringUtill.isStringEmpty(user.getUsername())) {
			MessageUtil.setSimpleBackMessage(backMessage, false,
					"输入有误!( ^_^ )? ");
			return backMessage;
		}
		if (StringUtill.contains_sqlinject_illegal_ch(user.getUsername())) {
			MessageUtil
					.setSimpleBackMessage(backMessage, false, "用户名请不要包含特殊字符");
			return backMessage;
		}

		// get private key from session
		String PRIVATE_KSY = (String) session.getAttribute("PRIVATE_KEY");
		String passwd = RSAUtils.decryptDataOnJava(user.getPassword(),
				PRIVATE_KSY);
		repassword = RSAUtils.decryptDataOnJava(repassword, PRIVATE_KSY);
		
		if (StringUtill.contains_sqlinject_illegal_ch(passwd)) {
			MessageUtil
					.setSimpleBackMessage(backMessage, false, "密码请不要包含特殊字符");
			return backMessage;
		}
		
		if (!userService.validPwd(passwd)) {
			MessageUtil.setSimpleBackMessage(backMessage, false,
					"密码至少六位(╯#-_-)╯~~~~~~~~~~~~~~~~~╧═╧  ");
			return backMessage;
		}
		if (StringUtill.isStringEmpty(repassword)) {
			MessageUtil.setSimpleBackMessage(backMessage, false,
					"请再次输入你的密码( ^_^ )? !");
			return backMessage;
		}
		if (StringUtill.isContainsChinese(user.getUsername())) {
			MessageUtil.setSimpleBackMessage(backMessage, false,
					"请使用英文名!..@_@|||||..");
			return backMessage;
		}
		List<User> users = userService.findByUserName(user.getUsername());
		if (!users.isEmpty()) {
			MessageUtil.setSimpleBackMessage(backMessage, false,
					"该名称已被使用!..@_@|||||..");
			return backMessage;
		}
		if (!passwd.equals(repassword)) {
			MessageUtil.setSimpleBackMessage(backMessage, false,
					"两次输入密码相同!..@_@|||||..");
			return backMessage;
		}
		MessageUtil.setSimpleBackMessage(backMessage, true,
				"恭喜成为日向博客新成员!(^_^)∠※ 送你一束花。");
		return backMessage;
	}

}
