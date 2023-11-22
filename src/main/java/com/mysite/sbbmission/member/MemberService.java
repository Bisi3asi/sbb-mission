package com.mysite.sbbmission.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String signInId, String username, String email, String password) {
        // todo : make signup form
        Member member = Member.builder()
                .signInId(signInId)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);
        return member;
    }
}
