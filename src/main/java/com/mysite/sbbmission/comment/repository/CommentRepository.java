package com.mysite.sbbmission.comment.repository;

import com.mysite.sbbmission.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // JpaRepository<Entity T, PK T>
}
