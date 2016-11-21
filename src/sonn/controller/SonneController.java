package sonn.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sonn.util.PageInfo;

/**
* @ClassName: AuthorPageController
* @Description: about author and sonne history
* @author sonne
* @date 2016-11-20
* @version 1.0
 */
@Controller
@RequestMapping("/sonne")
public class SonneController {

    /*
     * sonne pics
     */
    @RequestMapping(value = "/sonne_pic", method = RequestMethod.GET)
    public String sonne_pic(HttpServletRequest request,PageInfo pageInfo,
    		                   Model model) throws Exception
    {
        return "sonne";
    }
    
    /*
     * sonne
     */
    @RequestMapping(value = "/sonne", method = RequestMethod.GET)
    public String sonne(HttpServletRequest request,PageInfo pageInfo,
    		                   Model model) throws Exception
    {
        return "aboutSonne";
    }
    
    /*
     * about blog
     */
    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public String blog(HttpServletRequest request,PageInfo pageInfo,
    		                   Model model) throws Exception
    {
        return "aboutBlog";
    }
    
}
