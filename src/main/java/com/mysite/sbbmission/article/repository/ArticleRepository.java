package com.mysite.sbbmission.article.repository;

import com.mysite.sbbmission.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // JpaRepository<Entity T, PK T>
    Page<Article> search(List<String> kwTypes, String kw, Pageable pageable);
}
