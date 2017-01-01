package sonn.daoimpl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import sonn.Order;
import sonn.dao.MessageDao;
import sonn.entity.Article;
import sonn.entity.Message;
import sonn.entity.User;
import sonn.enums.MsgIsRead;
import sonn.util.Page;
import sonn.util.PageInfo;

/**
* @ClassName: MessageDaoImpl 
* @Description: the implement of message dao
* @author sonne
* @date 2016-12-5 20:55:06 
* @version 1.0
 */
@Repository("messageDaoImpl")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {
	
	/**
	* @Title: getMessagesByUsrId 
	* @Description: used to get messages by searching the usr's id
	* @param @param reciever
	* @return List<Message>    
	* @throws
	 */
	@Override
	public Page<Message> getMessagesByUsrId(User reciever,  List<Order> orders, PageInfo pageInfo ) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
		Root<Message> root = criteriaQuery.from(Message.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (reciever != null) 
		{
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("reciever"), reciever));
		}
		criteriaQuery.where(restrictions);
		//return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
		return super.findPage(criteriaQuery, pageInfo, Message.class, orders);
	}
	
	
	/**
	* @Title: delete_msgs_by_article 
	* @Description: used to delete all messages by searching the article
	* @param @param Article article
	* @return    
	* @throws
	 */
	@Override
	public void delete_msgs_by_article(Article article ) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
		Root<Message> root = criteriaQuery.from(Message.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (article != null) 
		{
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("article"), article));
		}
		criteriaQuery.where(restrictions);
		List<Message> msg_lst = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
		for (Message msg:msg_lst) {
			super.remove(msg);
		}
	}

	/**
	* @Title: getMessagesByUsrId 
	* @Description: used to get messages by searching the usr's id
	* @param @param reciever
	* @return List<Message>    
	* @throws
	 */
	@Override
	public long hasMsg(User reciever) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
		Root<Message> root = criteriaQuery.from(Message.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (reciever != null) 
		{
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("reciever"), reciever));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("is_read"), MsgIsRead.No));
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, Message.class);
	}

}
