package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author 无名
 * @date 2016.04.21  2016-5-1添加主键生成策略
 *       2016-05-15改变存储user到username
 * @description:文章实体类
 */
@Entity
public class Article 
{
	/*id*/
	private int id;
	
	/*标题*/
	private String title;
	
	/*文章路径*/
	private String articleAddr;
	
	/*文章内容*/
	private String content;
	
	/*作者*/
//	private User author;
	
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
	
	public String getArticleAddr() {
		return articleAddr;
	}

	public void setArticleAddr(String articleAddr) {
		this.articleAddr = articleAddr;
	}

	public String getAuthorName()
	{
		return authorName;
	}

	public void setAuthorName(String authorName)
	{
		this.authorName = authorName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
