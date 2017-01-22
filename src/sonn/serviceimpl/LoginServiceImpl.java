package sonn.serviceimpl;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import sonn.service.LoginService;
import sonn.service.UserService;
import sonn.util.RSAUtils;

/**
* @ClassName: LoginServiceImpl 
* @Description: none
* @author sonne
* @date 2017-1-21 14:19:52 
* @version 1.0
 */
@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService {

	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	/*
	 * common pretreatment of logincontroller
	 */
	@Override
	public void loginCommonPretreatment(HttpServletRequest request, Model model) throws Exception {
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
	}

}
