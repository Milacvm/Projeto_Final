package com.educandoweb.course.dto;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.educandoweb.course.entities.Post;
import com.educandoweb.course.entities.User;

public class PostDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Instant moment;

	@NotEmpty(message = "can't be empty")
	@Length(min = 5, max = 80, message = "length must be between 5 and 80")
	private String title;

	@NotEmpty(message = "can't be empty")
	private String body;

	private String author;
	private Long authorId;

	public PostDTO() {

	}

	public PostDTO(Post entity) {
		this.id = entity.getId();
		this.moment = entity.getMoment();
		this.title = entity.getTitle();
		this.body = entity.getBody();
		this.author = entity.getAuthor().getName();
		this.authorId = entity.getAuthor().getId();
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Post toEntity() {
		User userAuthor = new User(authorId, author, null, null, null);
		return new Post(id, moment, title, body, userAuthor);

	}
}
