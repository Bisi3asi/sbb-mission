package com.mysite.sbbmission.comment;

import com.mysite.sbbmission.article.Article;
import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import com.mysite.sbbmission.member.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment create(Article article, CommentForm commentForm, Member author) {
        Comment comment = Comment.builder()
                .content(commentForm.getContent().trim())
                .article(article)
                .author(author)
                .build();
        commentRepository.save(comment);
        return comment;
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
                .build();
    }

    @Transactional
    public void delete(Comment comment){
        commentRepository.delete(comment);
    }

    @Transactional
    public void addLike(Comment comment, Member member) {
        comment.getLiker().add(member);
    }

    @Transactional
    public void removeLike(Comment comment, Member member) {
        comment.getLiker().remove(member);
    }

    @Transactional
    public void addHate(Comment comment, Member member) {
        comment.getHater().add(member);
    }

    @Transactional
    public void removeHate(Comment comment, Member member) {
        comment.getHater().remove(member);
    }

}
