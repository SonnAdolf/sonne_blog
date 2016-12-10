package sonn.service;

import java.util.List;

import sonn.entity.Comment;

/**
* @ClassName: CommentService 
* @Description: service interface of comment
* @author sonne
* @date 2016-12-5 20:50:27 
*       2016-12-07 comments sort function
* @version 1.0
 */
public interface CommentService  extends BaseService<Comment> {
	
	public List<Comment> sort(List<Comment> comments);

}
