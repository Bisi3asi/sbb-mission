package com.mysite.sbbmission.article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // JpaRepository<Entity T, PK T>
}
