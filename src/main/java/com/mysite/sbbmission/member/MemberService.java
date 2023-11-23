package com.mysite.sbbmission.member;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String signInId, String username, String email, String password) {
        if (memberRepository.findBySignInId(signInId).isPresent()){
            throw new DataIntegrityViolationException(MemberUtilConst.SIGNIN_ID.UNUNIQUE_MESSAGE);
        }
        if (memberRepository.findByUsername(username).isPresent()){
            throw new DataIntegrityViolationException(MemberUtilConst.USERNAME.UNUNIQUE_MESSAGE);
        }
        if (memberRepository.findByEmail(email).isPresent()){
            throw new DataIntegrityViolationException(MemberUtilConst.EMAIL.UNUNIQUE_MESSAGE);
        }

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
