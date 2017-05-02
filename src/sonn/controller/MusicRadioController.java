package sonn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sonn.util.IOUtils;

/**
* @ClassName: MusicRadioController 
* @Description: Music Radio for CaiCai
* @author sonne
* @date 2017-5-2 21:43:13 
* @version 1.0
 */
@Controller
@RequestMapping("/radio")
public class MusicRadioController {
	
	private final String music_path = "/root/music";
	
	/*
	 * song list page.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) throws Exception {
		List<String> file_lst = IOUtils.getFileListOfFolder(music_path);
		model.addAttribute("music_lst", file_lst);
		return "radioPage";
	}
	
	/*
	 * music player page.
	 */
	@RequestMapping(value = "/player", method = RequestMethod.GET)
	public String player(String path, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("path", path);
		return "musicPlayerPage";
	}
}
