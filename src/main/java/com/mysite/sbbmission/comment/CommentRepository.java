package com.mysite.sbbmission.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // JpaRepository<Entity T, PK T>
}
