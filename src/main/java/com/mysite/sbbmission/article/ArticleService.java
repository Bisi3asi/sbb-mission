package com.mysite.sbbmission.article;

import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Page<Article> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
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
