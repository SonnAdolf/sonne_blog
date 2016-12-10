package sonn.daoimpl;

import org.springframework.stereotype.Repository;

import sonn.dao.CommentDao;
import sonn.entity.Comment;

/**
* @ClassName: CommentDaoImpl 
* @Description: the implement of comment dao
* @author sonne
* @date 2016-12-5 20:55:06 
* @version 1.0
 */
@Repository("commentDaoImpl")
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements CommentDao {

}
