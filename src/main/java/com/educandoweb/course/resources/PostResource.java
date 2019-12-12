package com.educandoweb.course.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.course.dto.CommentDTO;
import com.educandoweb.course.dto.PostDTO;
import com.educandoweb.course.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired 
	private PostService service;
	
	@GetMapping
	public ResponseEntity<List<PostDTO>> findByAuthor() {
		List<PostDTO> list = service.findByAuthor();
		return ResponseEntity.ok().body(list);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
	@GetMapping(value = "/user/{userId}")
	public ResponseEntity<PostDTO> findById(@PathVariable Long userId) {
		PostDTO dto = service.findById(userId);
		return ResponseEntity.ok().body(dto);
	}
	@PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
	@GetMapping(value = "/{id}/comments")
	public ResponseEntity<List<CommentDTO>> findComments(@PathVariable Long id) {
		List<CommentDTO> list = service.findComments(id);
		return ResponseEntity.ok().body(list);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
	@PostMapping
	public ResponseEntity<PostDTO> insert(@Valid @RequestBody PostDTO dto) {
		PostDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<PostDTO> update(@PathVariable Long id, @Valid @RequestBody PostDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<PostDTO>> findAllPaged(
            @RequestParam(value = "body", defaultValue = "") String body,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<PostDTO> list = service.findByBodyPaged(body, pageRequest);

        return ResponseEntity.ok().body(list);
    }
}