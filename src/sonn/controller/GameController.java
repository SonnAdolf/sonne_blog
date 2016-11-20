package sonn.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sonn.util.PageInfo;

/**
* @ClassName: GameController 
* @Description: Using javascript develop web game.
* @author sonne
* @date 2016-11-18 
* @version 1.0
 */
@Controller
@RequestMapping("/game")
public class GameController {

    /*
     * Game page, just for fun!
     */
    @RequestMapping(value = "/snake", method = RequestMethod.GET)
    public String snake(HttpServletRequest request,PageInfo pageInfo,
    		                   Model model) throws Exception
    {
        return "snakeGame";
    }
    
}
