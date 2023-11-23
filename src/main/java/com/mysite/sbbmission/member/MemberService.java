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

    public Member create(MemberSignUpForm memberSignUpForm) {
        StringBuilder exceptionBuilder = new StringBuilder();
        if (memberRepository.findBySignInId(memberSignUpForm.getSignInId()).isPresent())
            exceptionBuilder.append(MemberUtilConst.SIGNIN_ID.UNUNIQUE_MESSAGE);
        if (memberRepository.findByUsername(memberSignUpForm.getUsername()).isPresent())
            exceptionBuilder.append(MemberUtilConst.USERNAME.UNUNIQUE_MESSAGE);
        if (memberRepository.findByEmail(memberSignUpForm.getEmail()).isPresent())
            exceptionBuilder.append(MemberUtilConst.EMAIL.UNUNIQUE_MESSAGE);

        if (!exceptionBuilder.isEmpty())
            throw new DataIntegrityViolationException(exceptionBuilder.toString());

        Member member = Member.builder()
                .signInId(memberSignUpForm.getSignInId())
                .username(memberSignUpForm.getUsername())
                .email(memberSignUpForm.getEmail())
                .password(passwordEncoder.encode(memberSignUpForm.getPassword1()))
                .build();

        memberRepository.save(member);
        return member;
    }
}
