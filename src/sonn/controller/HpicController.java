package sonn.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sonn.entity.User;
import sonn.service.UserService;
import sonn.util.IOUtils;
import sonn.util.PageInfo;

/**
* @ClassName: HpicController 
* @Description: config the usr's h_pic
* @author sonne
* @date 2016-12-4 13:30:18 
* @version 1.0
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/head")
public class HpicController {
	
    @Resource(name = "userServiceImpl")
    private UserService userService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpServletRequest request, PageInfo pageInfo,
			Model model) throws Exception {
		return "uploadPicPage";
	}

	@RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    @ResponseBody
    public boolean uploadPic(HttpServletRequest request, 
    		         HttpServletResponse response) throws Exception {
    	DiskFileUpload diskFileUpload = new DiskFileUpload();
    	try {
			@SuppressWarnings("unchecked")
			List<FileItem> list = diskFileUpload.parseRequest(request);
    		for (FileItem fileItem : list) {
    			if(fileItem.isFormField()) 
    				;
    			else {
    				if ("pic".equals(fileItem.getFieldName())) {
    					String username = userService
    							.getUsernameFromSession(request);
    					if (username == null) {
    						return false;
    					}
    					String path = getPathFromSession(username,request);
    					String fileName = new String(fileItem.getName().getBytes(),"utf-8");
    					// because one user only have one h_pic, delete the folder's pic
    					IOUtils.delAllFile(path);
    					
    					// because every usr only has one profile picture 
    					// so that picture can use a default name of '1.jpg'
    					String pathOfDefaultName = path + "/" + "1.jpg";
    					
    					String localPicPath = path + "/" + fileName;
    					File picFile = new File(localPicPath);
    					picFile.createNewFile();
    					InputStream ins = fileItem.getInputStream();
    					OutputStream ous = new FileOutputStream(picFile);
    					try {
    						byte[] buffer = new byte[1024];
    						int len = 0;
    						while((len=ins.read(buffer)) > -1)
    							ous.write(buffer, 0, len);
    					} finally {
    						ous.close();
    						ins.close();
    					}
    					
    					// change the profile picture's name of local path
    					IOUtils.renameFile(path, fileName, "1.jpg");
    					
    					// compress the picture.
    					IOUtils.reduceImg(pathOfDefaultName, pathOfDefaultName, 200, 200, null);
    					
    					// uodate user of mysql db
    					User user = userService.findByUserName(username).get(0);
    					path = IOUtils.getRelativePath(pathOfDefaultName);
    					user.setH_pic_path(path);
    					userService.update(user);
    				}
    			}
    		}
    	}catch (FileUploadException e) {
    		return false;
    	}
    	return true;
    }
	
	private String getPathFromSession(String username, 
			                    HttpServletRequest request) {		
		// the path of the user to save the pics
		String basePath = request.getSession().getServletContext().getRealPath("/");
    	String path = basePath + "h_pics/" + username;
    	File file = new File(path);
    	if (!file.exists() && !file.isDirectory()) {
    		file.mkdirs();
    	}
    	return path;
	}
}
