package com.mysite.sbbmission.comment;

import com.mysite.sbbmission.article.Article;
import com.mysite.sbbmission.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void create(Article article, String content, Member author) {
        Comment comment = Comment.builder()
                .content(content.trim())
                .createDateTime(LocalDateTime.now())
                .article(article)
                .author(author)
                .build();
        commentRepository.save(comment);
    }
}
