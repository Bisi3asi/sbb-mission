package com.mysite.sbbmission;

import com.mysite.sbbmission.article.Article;
import com.mysite.sbbmission.article.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class SbbMissionApplicationTests {
    @Autowired
    private ArticleRepository articleRepository;

    // Test : Case, When, Then
    @Test
    void 글_추가() {
        // case 1
        Article a1 = Article.builder()
                .subject("글 1")
                .content("내용 1")
                .createDateTime(LocalDateTime.MAX)
                .modifiedDateTime(LocalDateTime.MAX)
                .build();

        // case 2
        Article a2 = Article.builder()
                .subject("글 2")
                .content("내용 2")
                .createDateTime(LocalDateTime.MAX)
                .modifiedDateTime(LocalDateTime.MAX)
                .build();

        // case 3
        Article a3 = Article.builder()
                .subject("글 3")
                .content("내용 3")
                .createDateTime(LocalDateTime.MAX)
                .modifiedDateTime(LocalDateTime.MAX)
                .build();

        // when, then
        articleRepository.save(a1);
        articleRepository.save(a2);
        articleRepository.save(a3);
    }
}
