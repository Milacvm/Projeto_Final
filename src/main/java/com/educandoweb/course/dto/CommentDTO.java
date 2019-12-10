package com.educandoweb.course.dto;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotEmpty;

import com.educandoweb.course.entities.Comment;
import com.educandoweb.course.entities.Post;
import com.educandoweb.course.entities.User;

public class CommentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Instant moment;

	private Long postId;

	private Long authorId;
	
	@NotEmpty(message = "can't be empty")
    private String text;

	public CommentDTO(Long id, Instant moment, String postTitle, Long postId, Long authorId,
			String text) {
		super();
		this.id = id;
		this.moment = moment;
		this.postId = postId;
		this.authorId = authorId;
		this.text = text;
	}

	public CommentDTO(Comment entity) {
		this.id = entity.getId();
		this.moment = entity.getMoment();
		this.postId = entity.getPost().getId();
		this.authorId = entity.getAuthor().getId();
		this.text = entity.getText();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Comment toEntity() {
		User userAuthor = new User(authorId,null, null, null, null);
		Post post = new Post(postId, null, null, null, null);
		
		return new Comment(id, moment, text, post, userAuthor);

	}

}
