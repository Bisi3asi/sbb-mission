package com.mysite.sbbmission.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Spring Security
// Member <- UserDetail
// MemberService <- UserDetailsService

// Security
// SigninId  <- username
// password <- password


@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    // 사용자명으로 비밀번호를 조회하여 리턴하는 메서드
    public UserDetails loadUserByUsername(String signInId) throws UsernameNotFoundException {
        Optional<Member> opMember = this.memberRepository.findBySignInId(signInId);
        if (opMember.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Member member = opMember.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("sbbadmin".equals(signInId)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        return new User(member.getSignInId(), member.getPassword(), authorities);
    }
}