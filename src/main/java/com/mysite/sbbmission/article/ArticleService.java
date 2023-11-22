package com.mysite.sbbmission.article;

import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Page<Article> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    public Article getArticle(Long id){
        Optional<Article> article = this.articleRepository.findById(id);

        if (article.isPresent()){
            return article.get();
        }
        else {
            throw new DataNotFoundException("존재하지 않는 게시물입니다");
        }
    }

    public void create(String title, String content){
        Article article = Article.builder()
                .title(title.trim())
                .content(content.trim())
                .createDateTime(LocalDateTime.now())
                .build();
        articleRepository.save(article);
    }
}
