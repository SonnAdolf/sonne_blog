package sonn.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @ClassName: Comment
 * @Description: entity of comment
 * @author sonne
 * @date 2016-12-5 19:41:11
 * @version 1.0
 */
@Entity
public class Comment {
	/* id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/* content of the comment */
	private String content;

	/* is this article's comment */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;

	/* date of comment */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	/* name of the author */
	private String authorName;
	
	/* quote other comment */
//	@OneToOne(fetch = FetchType.LAZY)
//	private Comment quote;

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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

//	public Comment getQuote() {
//		return quote;
//	}
//
//	public void setQuote(Comment quote) {
//		this.quote = quote;
//	}

}
