package com.mysite.sbbmission.member;

import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    // id(중복) 에러메시지 pw, pw1, 닉네임(중복), 이메일(중복)
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
                .build();

        memberRepository.save(member);
        // return member;
    }

    public Member getMember(String signInId) {
        Optional<Member> member = this.memberRepository.findBySignInId(signInId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("존재하지 않는 사용자입니다.");
        }
    }
}
