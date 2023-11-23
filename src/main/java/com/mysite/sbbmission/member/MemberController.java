package com.mysite.sbbmission.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signUp(MemberSignUpForm memberSignUpForm) {
        return "member/signup_form";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("memberSignUpForm") @Valid MemberSignUpForm memberSignUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signup_form";
        }
        if (!memberSignUpForm.getPassword1().equals(memberSignUpForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    MemberUtilConst.PASSWORD.PW1PW2_UNMATCH);
            return "member/signup_form";
        }
        try {
            memberService.create(memberSignUpForm);
            // 아이디, 사용자, 이메일 중복 발생 시 view에 에러 전송
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains(MemberUtilConst.SIGNIN_ID.UNUNIQUE_MESSAGE))
                bindingResult.rejectValue("signInId", "signInIdExists",
                        MemberUtilConst.SIGNIN_ID.UNUNIQUE_MESSAGE);
            if (e.getMessage().contains(MemberUtilConst.USERNAME.UNUNIQUE_MESSAGE))
                bindingResult.rejectValue("username", "usernameExists",
                        MemberUtilConst.USERNAME.UNUNIQUE_MESSAGE);
            if (e.getMessage().contains(MemberUtilConst.EMAIL.UNUNIQUE_MESSAGE))
                bindingResult.rejectValue("email", "emailExists",
                        MemberUtilConst.EMAIL.UNUNIQUE_MESSAGE);
            return "member/signup_form";
        } catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "member/signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/signin")
    public String signIn(){
        return "member/signin_form.html";
    }
}

