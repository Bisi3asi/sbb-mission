package com.mysite.sbbmission.member;

import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(MemberSignUpForm memberSignUpForm) {
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
                .createDateTime(LocalDateTime.now())
                .build();

        memberRepository.save(member);
        // return member;
    }

    public Member getMember(String signInId) {
        Optional<Member> member = this.memberRepository.findBySignInId(signInId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }
}
