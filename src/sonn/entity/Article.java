package sonn.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import sonn.annotation.IsValidString;


/**
 * @author sonne
 * @date 2016.04.21  2016-5-1添加主键生成策略
 *       2016-05-15改变存储user到username
 *       2016-11-11 article summary
 * @description:article entity class
 */
@Entity
public class Article 
{
	/*id*/
	private int id;
	
	/*title*/
	@IsValidString
	private String title;
	
	/*article path*/
	private String articleAddr;
	
	/*the local path of the summary*/
	private String summaryAddr;

	/*content of the article*/
	private String content;
	
	/*article summary*/
	private String summary;

	/*name of the author*/
	private String authorName;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
}
