package com.mysite.sbbmission.comment;

import com.mysite.sbbmission.article.Article;
import com.mysite.sbbmission.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT", length = 300)
    private String content;

    @CreatedDate
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @ManyToOne(fetch = FetchType.LAZY) // default 값인 EAGER를 사용하지만
    private Article article;

    @ManyToOne
    private Member author;

    @ManyToMany
    Set<Member> liker;

    @ManyToMany
    Set<Member> hater;
}
