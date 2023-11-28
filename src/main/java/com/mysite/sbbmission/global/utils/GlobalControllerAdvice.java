package com.mysite.sbbmission.global.utils;

import com.mysite.sbbmission.member.Member;
import com.mysite.sbbmission.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final MemberService memberService;

    @ModelAttribute
    public void addSignedInMemberToModel(Model model, Principal principal){
        if (principal != null){
            Member member = memberService.getMember(principal.getName());
            model.addAttribute("member", member);
        }
    }
}
