package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.course.dto.CommentDTO;
import com.educandoweb.course.dto.PostDTO;
import com.educandoweb.course.entities.Comment;
import com.educandoweb.course.entities.Post;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.PostRepository;
import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;

	@Autowired
	private AuthService authService;

	public List<PostDTO> findAll() {
		List<Post> list = repository.findAll();
		return list.stream().map(e -> new PostDTO(e)).collect(Collectors.toList());
	}

	public PostDTO findById(Long id) {
		authService.validateSelfOrAdmin(id);
		Optional<Post> obj = repository.findById(id);
		Post entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new PostDTO(entity);
	}

	public PostDTO insert(PostDTO dto) {
		Post entity = dto.toEntity();
		entity = repository.save(entity);
		return new PostDTO(entity);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Transactional
	public PostDTO update(Long id, PostDTO dto) {
		authService.validateSelfOrAdmin(id);
		try {
			Post entity = repository.getOne(id);
			updateData(entity, dto);
			entity = repository.save(entity);
			return new PostDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Post entity, PostDTO dto) {
		entity.setId(dto.getId());
		entity.setMoment(dto.getMoment());
		entity.setTitle(dto.getTitle());
		entity.setBody(dto.getBody());
	}

	public Page<PostDTO> findByBodyPaged(String body, Pageable pageable) {
			Page<Post> list;
			list = repository.findByBodyContainingIgnoreCase(body, pageable);
			return list.map(e -> new PostDTO(e));
	}

	public List<CommentDTO> findComments(Long id) {
		Post post = repository.getOne(id);
		Set<Comment> set = post.getComments();
		return set.stream().map(e -> new CommentDTO(e)).collect(Collectors.toList());
	}

	public List<PostDTO> findByAuthor() {
		User author = authService.authenticated();
		List<Post> list = repository.findByAuthor(author);
		return list.stream().map(e -> new PostDTO(e)).collect(Collectors.toList());
}
	
}