package sonn.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sonn.Order;
import sonn.dao.MessageDao;
import sonn.entity.Article;
import sonn.entity.Message;
import sonn.entity.User;
import sonn.service.MessageService;
import sonn.util.Page;
import sonn.util.PageInfo;

/**
* @ClassName: MessageServiceImpl 
* @Description: the implementation of service interface of message
* @author sonne
* @date 2016-12-23 21:16:46 
*       2017-02-02 findMsgsByArticle
* @version 1.0
 */
@Service("messageServiceImpl")
public class MessageServiceImpl  extends BaseServiceImpl<Message> implements MessageService {

	@Resource(name = "messageDaoImpl")
	private MessageDao messageDao;

	@Resource(name = "messageDaoImpl")
	public void setBaseDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
	}

	/**
	* @Title: getMessagesByUsrId 
	* @Description: used to get messages by searching the usr's id
	* @param @param reciever
	* @return List<Message>    
	* @throws
	 */
	@Override
	public Page<Message> getMessagesByUsrId(User reciever,  List<Order> orders, PageInfo pageInfo) {
		return messageDao.getMessagesByUsrId(reciever,orders,pageInfo);
	}

	@Override
	public boolean hasMsg(User reciever) {
		if (messageDao.hasMsg(reciever) > 0) {
			return true;
		}
		return false;
	}

	/**
	* @Title: getMessagesByUsrId 
	* @Description: used to get messages by searching the usr's id
	* @param @param reciever
	* @return List<Message>    
	* @throws
	 */
	@Override
	public void delete_msgs_by_article(Article article) {
		messageDao.delete_msgs_by_article(article);
	}

	/**
	* @Title: findMsgsByArticle 
	* @Description: find messages of a article
	* @param @param article
	* @param @return    articles 
	* @return List<Message>    ∑µªÿ¿‡–Õ 
	* @throws
	 */
	@Override
	public List<Message> findMsgsByArticle(Article article) {
		return messageDao.findMsgsByArticle(article);
	}
	
}