package sonn.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error")
public class ErrorController {

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String error404(HttpServletRequest request) throws Exception {
		return "404-error";
	}

	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String error505(HttpServletRequest request) throws Exception {
		return "error";
	}
	
	@RequestMapping(value = "/400", method = RequestMethod.GET)
	public String error400(HttpServletRequest request) throws Exception {
		return "error";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String error403(HttpServletRequest request) throws Exception {
		return "error";
	}
}
