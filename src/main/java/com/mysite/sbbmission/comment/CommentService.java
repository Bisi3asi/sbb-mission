package com.mysite.sbbmission.comment;

import com.mysite.sbbmission.article.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void create(Article article, String content) {
        Comment comment = Comment.builder()
                .content(content)
                .createDateTime(LocalDateTime.now())
                .article(article)
                .build();
        commentRepository.save(comment);
    }
}
