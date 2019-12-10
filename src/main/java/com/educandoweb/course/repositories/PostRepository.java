package com.educandoweb.course.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.course.entities.Post;
import com.educandoweb.course.entities.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Transactional(readOnly = true)
    @Query("SELECT obj FROM Post obj WHERE obj.author = :author")
	Page<Post> findByAuthorPaged(@Param("author") User author, Pageable pageable);

 
    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Post obj WHERE LOWER(obj.body) LIKE LOWER(CONCAT('%',:body,'%'))")
    Page<Post> findByBodyContainingIgnoreCase(String body, Pageable pageable);
    
    List<Post> findByAuthor(User author);
}
