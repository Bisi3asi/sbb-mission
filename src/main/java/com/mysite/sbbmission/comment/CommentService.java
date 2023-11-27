package com.mysite.sbbmission.comment;

import com.mysite.sbbmission.article.Article;
import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import com.mysite.sbbmission.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void create(Article article, CommentForm commentForm, Member author) {
        Comment comment = Comment.builder()
                .content(commentForm.getContent().trim())
                .createDateTime(LocalDateTime.now())
                .article(article)
                .author(author)
                .build();
        commentRepository.save(comment);
    }

    public Comment getComment(long id){
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()){
            return comment.get();
        }
        else throw new DataNotFoundException("존재하지 않는 댓글입니다.");
    }

    @Transactional
    public void update(Comment comment, CommentForm commentForm){
        comment = comment.toBuilder()
                .content(commentForm.getContent())
                .modifiedDateTime(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(Comment comment){
        commentRepository.delete(comment);
    }

}
