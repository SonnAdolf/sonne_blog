package sonn.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sonn.dao.CommentDao;
import sonn.entity.Comment;
import sonn.service.CommentService;

/**
* @ClassName: CommentServiceImpl 
* @Description: the implement of service of comment
* @author sonne
* @date 2016-12-5 20:49:37 
*       2016-12-07 comments sort function
* @version 1.0
 */
@Service("commentServiceImpl")
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

	@Resource(name = "commentDaoImpl")
	private CommentDao commentDao;
	
	@Resource(name = "commentDaoImpl")
	public void setBaseDao(CommentDao commentDao)
	{
		super.setBaseDao(commentDao);
	}

	@Override
	public List<Comment> sort(List<Comment> comments) {
		int commentsLength = comments.size();
		Comment tmp = new Comment();
		for (int i = 0; i < commentsLength; i++)
		{
			int min = i;
			for(int j = i + 1; j < commentsLength; j++)
			{
				if(comments.get(j).getDate().before(comments.get(min).getDate())) 
				{
					min = j;
				}
			}
			tmp = comments.get(min);
			comments.set(min, comments.get(i));
			comments.set(i, tmp);
		}
		
		//ÉèÖÃÆÀÂÛÂ¥²ã
		for (int n = 0; n < commentsLength; n++) {
			comments.get(n).setFloor(n + 1);
		}
		
		return comments;
	}
	
}
