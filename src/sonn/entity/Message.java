package sonn.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import sonn.enums.MsgIsRead;
import sonn.enums.MsgType;

/**
* @ClassName: Message 
* @Description: entity class of message
* @author sonne
* @date 2016-12-23 20:32:16 
* @version 1.0
 */
@Entity
public class Message {

	/*id*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String content;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User sender;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User reciever;
	
	private MsgType type;
	
	private MsgIsRead is_read;
	
	private Date date;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Article article;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReciever() {
		return reciever;
	}

	public void setReciever(User reciever) {
		this.reciever = reciever;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public MsgIsRead getIs_read() {
		return is_read;
	}

	public void setIs_read(MsgIsRead is_read) {
		this.is_read = is_read;
	}
	
}
