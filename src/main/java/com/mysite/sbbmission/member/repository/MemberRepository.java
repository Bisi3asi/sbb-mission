package com.mysite.sbbmission.member.repository;


import com.mysite.sbbmission.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findBySignInId(String signInId);

    public Optional<Member> findByUsername(String username);

    public Optional<Member> findByEmail(String email);
}
