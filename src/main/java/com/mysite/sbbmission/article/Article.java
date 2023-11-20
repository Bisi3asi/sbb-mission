package com.mysite.sbbmission.article;

import com.mysite.sbbmission.comment.Comment;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "article",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY) // default 값인 EAGER를 사용하지만,
    private List<Comment> commentList;
}
