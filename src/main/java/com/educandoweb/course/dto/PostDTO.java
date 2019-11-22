package com.educandoweb.course.dto;

import java.io.Serializable;
import java.time.Instant;

import com.educandoweb.course.entities.Post;
import com.educandoweb.course.services.validation.UserUpdateValid;

@UserUpdateValid
public class PostDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Instant moment;

	private String title;

	private String body;

	public PostDTO(Long id, Instant moment, String title, String body) {
		super();
		this.id = id;
		this.moment = moment;
		this.title = title;
		this.body = body;
	}
	
	public PostDTO(){
		
	}
	public PostDTO(Post entity) {
		this.id = entity.getId();
		this.moment = entity.getMoment();
		this.title = entity.getTitle();
		this.body = entity.getBody();
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
	
	public Post toEntity() {
		return new Post(id, moment, title, body);
	
    }
}
