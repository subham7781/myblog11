package com.myblog.myblog11.repository;

import com.myblog.myblog11.entity.Comment;
import com.myblog.myblog11.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
