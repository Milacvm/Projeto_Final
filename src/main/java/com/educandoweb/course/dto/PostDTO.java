package com.educandoweb.course.dto;

import java.io.Serializable;
import java.time.Instant;

import com.educandoweb.course.entities.Post;
import com.educandoweb.course.entities.User;

public class PostDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Instant moment;

	private String title;

	private String body;
	
	private Long authorId;

	public PostDTO(Long id, Instant moment, String title, String body, Long authorId) {
		super();
		this.id = id;
		this.moment = moment;
		this.title = title;
		this.body = body;
		this.setAuthorId(authorId);
	}
	
	public PostDTO(){
		
	}
	public PostDTO(Post entity) {
		this.id = entity.getId();
		this.moment = entity.getMoment();
		this.title = entity.getTitle();
		this.body = entity.getBody();
		this.setAuthorId(entity.getAuthor().getId());
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


	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
		
	public Post toEntity() {
		User author = new User(authorId, null, null, null, null);
		return new Post(id, moment, title, body, author);
	
    }
}
