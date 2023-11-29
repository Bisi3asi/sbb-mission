package com.mysite.sbbmission.global.utils;

import com.mysite.sbbmission.article.entity.Article;
import com.mysite.sbbmission.article.repository.ArticleRepository;
import com.mysite.sbbmission.comment.entity.Comment;
import com.mysite.sbbmission.comment.repository.CommentRepository;
import com.mysite.sbbmission.member.model.entity.Member;
import com.mysite.sbbmission.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("!prod")
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class init {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void setInitData() {
        Member member = Member.builder()
                .signInId("testuser1")
                .username("testuser1")
                .password("12345678")
                .build();
        memberRepository.save(member);

        member = Member.builder()
                .signInId("sbbadmin")
                .username("admin")
                .password(passwordEncoder.encode("12345678"))
                .build();
        memberRepository.save(member);

        for (int i = 1; i < 30; i++) {
            Article article = Article.builder()
                    .title(String.format("테스트 글 %d", i))
                    .content(String.format("테스트 내용 %d", i))
                    .author(member)
                    .build();
            articleRepository.save(article);

            for (int j = 0; j < 3; j++) {
                Comment comment = Comment.builder()
                        .article(article)
                        .author(member)
                        .content(String.format("테스트 댓글 %d", i))
                        .build();
                commentRepository.save(comment);
            }
        }
    }

}
