package com.mysite.sbbmission.member.model.entity;

import com.mysite.sbbmission.member.model.MemberUtilConst;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", length = MemberUtilConst.SIGNIN_ID.MAXLENGTH, unique = true)
    private String signInId;

    @Column(columnDefinition = "VARCHAR(10)", length = MemberUtilConst.USERNAME.MAXLENGTH, unique = true)
    private String username;

    @Column(columnDefinition = "VARCHAR(20)", length = MemberUtilConst.PASSWORD.MAXLENGTH)
    private String password;

    @Column(columnDefinition = "VARCHAR(40)", length = MemberUtilConst.EMAIL.MAXLENGTH, unique = true)
    private String email;

    @CreatedDate
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;
}