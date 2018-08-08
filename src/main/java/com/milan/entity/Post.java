package com.milan.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;


@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int postId;
	private String title;
	private String subtitle;
	@Column(columnDefinition="TEXT")
	private String gist;
	@Column(columnDefinition="TEXT")
	private String body;
	private String tag;
	private String image;
	@CreatedDate
	@Column(columnDefinition="TIMESTAMP")
	private Date postedOn = new Date();
	@ManyToOne
	private Author author;
	@OneToMany(mappedBy="post")
	private List<Comment> comments;
	
	public Post() {}

	public Post(int postId, String title, String subtitle, String gist, String body, String tag, String image, Date postedOn,
			Author author, List<Comment> comments) {
		super();
		this.postId = postId;
		this.title = title;
		this.subtitle = subtitle;
		this.gist = gist;
		this.body = body;
		this.tag = tag;
		this.image = image;
		this.postedOn = postedOn;
		this.author = author;
		this.comments = comments;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getGist() {
		return gist;
	}

	public void setGist(String gist) {
		this.gist = gist;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", title=" + title + ", subtitle=" + subtitle + ", gist=" + gist + ", body="
				+ body + ", tag=" + tag + ", image=" + image + ", postedOn=" + postedOn + ", author=" + author
				+ ", comments=" + comments + "]";
	}

	



	
	
	
	

}
