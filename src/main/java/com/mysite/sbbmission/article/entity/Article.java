package com.mysite.sbbmission.article.entity;

import com.mysite.sbbmission.comment.entity.Comment;
import com.mysite.sbbmission.member.model.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "VARCHAR(50)", length = 50)
    private String title;

    @Column(columnDefinition = "TEXT", length = 500)
    private String content;

    @CreatedDate
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne
    private Member author;

    @ManyToMany
    Set<Member> liker;

    @ManyToMany
    Set<Member> hater;
}
