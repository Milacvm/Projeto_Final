package com.educandoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.course.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
