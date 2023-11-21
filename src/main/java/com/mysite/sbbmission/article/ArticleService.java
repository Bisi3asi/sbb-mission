package com.mysite.sbbmission.article;

import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return this.articleRepository.findAll();
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
                .title(title)
                .content(content)
                .createDateTime(LocalDateTime.now())
                .build();
        articleRepository.save(article);
    }
}
