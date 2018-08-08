package com.milan.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import org.springframework.data.annotation.CreatedDate;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int commentId;
	private String userName;
	private String userImg;
	@Column(columnDefinition="TEXT")
	private String comment;
	@CreatedDate
	@Column(columnDefinition="TIMESTAMP")
	private Date commentPublished = new Date();
	private boolean liked;
	@ManyToOne
	private Post post;
	
	public Comment() {}

	public Comment(String userName, String userImg, String comment, Date commentPublished, boolean liked, Post post) {
		super();
		this.userName = userName;
		this.userImg = userImg;
		this.comment = comment;
		this.commentPublished = commentPublished;
		this.liked = liked;
		this.post = post;
	}

	
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCommentPublished() {
		return commentPublished;
	}

	public void setCommentPublished(Date commentPublished) {
		this.commentPublished = commentPublished;
	}
	
	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", userName=" + userName + ", userImg=" + userImg + ", comment="
				+ comment + ", commentPublished=" + commentPublished + ", liked=" + liked + ", post=" + post + "]";
	}

	
	
	
	
	
	
}
