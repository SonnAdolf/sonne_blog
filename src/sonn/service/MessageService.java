package sonn.service;

import java.util.List;

import sonn.Order;
import sonn.entity.Message;
import sonn.entity.User;
import sonn.util.Page;
import sonn.util.PageInfo;

/**
* @ClassName: MessageServiceImpl 
* @Description: the service interface of message
* @author sonne
* @date 2016-12-23 21:16:46 
* @version 1.0
 */
public interface MessageService extends BaseService<Message> {
	
	/**
	* @Title: getMessagesByUsrId 
	* @Description: used to get messages by searching the usr's id
	* @param @param reciever
	* @return List<Message>    
	* @throws
	 */
	Page<Message> getMessagesByUsrId(User reciever,  List<Order> orders, PageInfo pageInfo );
	
	boolean hasMsg(User reciever);
}
