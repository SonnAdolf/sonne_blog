package sonn.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import sonn.annotation.IsValidString;


/**
 * @author sonne
 * @date 2016.04.21  2016-5-1添加主键生成策略
 *       2016-05-15改变存储user到username
 *       2016-11-11 article summary
 *       2016-11-25 check by annotation
 *       2016-11-28 add date
 *       2016-12-27 read_time
 * @description:article entity class
 */
@Entity
public class Article 
{
	/*id*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/*title*/
	@NotNull
	@IsValidString
	@Length(min=1, max=40)
	private String title;
	
	/*article path*/
	private String articleAddr;
	
	/*the local path of the summary*/
	private String summaryAddr;

	/*content of the article*/
	private String content;
	
	/*article summary*/
	private String summary;
	
	@OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Comment> comments;

	/*name of the author*/
	@NotNull
	@IsValidString
	@Length(min=1, max=20)
	private String authorName;
	
	/* date of article */
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date date;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User author;
	
	/* how many times the link of the article has been clicked */
	private int read_times;
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public String getArticleAddr() 
	{
		return articleAddr;
	}

	public void setArticleAddr(String articleAddr) 
	{
		this.articleAddr = articleAddr;
	}

	public String getSummaryAddr()
	{
		return summaryAddr;
	}

	public void setSummaryAddr(String summaryAddr)
	{
		this.summaryAddr = summaryAddr;
	}
	
	public String getAuthorName()
	{
		return authorName;
	}

	public void setAuthorName(String authorName)
	{
		this.authorName = authorName;
	}

	public String getContent() 
	{
		return content;
	}

	public void setContent(String content) 
	{
		this.content = content;
	}
	
	
	public String getSummary() 
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public Date getDate() 
	{
		return date;
	}

	public void setDate(Date date) 
	{
		this.date = date;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public int getRead_times() {
		return read_times;
	}

	public void setRead_times(int read_times) {
		this.read_times = read_times;
	}
	
}
