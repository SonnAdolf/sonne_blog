package sonn.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

/**
* @ClassName: LoginService 
* @Description: none
* @author sonne
* @date 2017-1-21 14:19:18 
* @version 1.0
 */
public interface LoginService {
	
	/*
	 * common pretreatment of logincontroller
	 */
	public void loginCommonPretreatment(HttpServletRequest request,Model model) throws Exception; 
}
