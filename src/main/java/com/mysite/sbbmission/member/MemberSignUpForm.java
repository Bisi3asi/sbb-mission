package com.mysite.sbbmission.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class MemberSignUpForm {
    @Size(min = MemberUtilConst.SIGNIN_ID.MINLENGTH,
            max = MemberUtilConst.SIGNIN_ID.MAXLENGTH,
            message = MemberUtilConst.SIGNIN_ID.UNVALID_LENGTH_MESSAGE)
    @NotEmpty(message = MemberUtilConst.SIGNIN_ID.ISEMPTY_MESSAGE)
    private String signInID;

    @NotEmpty(message = MemberUtilConst.PASSWORD.ISEMPTY_MESSAGE)
    private String password1;

    @NotEmpty(message = MemberUtilConst.PASSWORD.ISEMPTY_MESSAGE_2)
    private String password2;

    @Size(max = MemberUtilConst.EMAIL.MAXLENGTH,
        message = MemberUtilConst.EMAIL.UNVALID_LENGTH_MESSAGE)
    @NotEmpty(message = MemberUtilConst.EMAIL.ISEMPTY_MESSAGE)
    @Email
    private String email;
}
