package com.mysite.sbbmission.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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