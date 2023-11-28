package com.mysite.sbbmission.article.repository;

import com.mysite.sbbmission.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // JpaRepository<Entity T, PK T>
    Page<Article> findAll(Pageable pageable);
    Page<Article> findAll(Specification<Article> spec, Pageable pageable);
}
