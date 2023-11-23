package com.mysite.sbbmission.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignUpForm {
    @NotEmpty(message = MemberUtilConst.SIGNIN_ID.ISEMPTY_MESSAGE)
    @Size(min = MemberUtilConst.SIGNIN_ID.MINLENGTH,
            max = MemberUtilConst.SIGNIN_ID.MAXLENGTH,
            message = MemberUtilConst.SIGNIN_ID.UNVALID_LENGTH_MESSAGE)
    private String signInId;

    @NotEmpty(message = MemberUtilConst.USERNAME.ISEMPTY_MESSAGE)
    @Size(min = MemberUtilConst.USERNAME.MINLENGTH,
            max = MemberUtilConst.USERNAME.MAXLENGTH,
            message = MemberUtilConst.USERNAME.UNVALID_LENGTH_MESSAGE)
    private String username;

    @NotEmpty(message = MemberUtilConst.PASSWORD.ISEMPTY_MESSAGE)
    @Size(min = MemberUtilConst.PASSWORD.MINLENGTH,
            max = MemberUtilConst.PASSWORD.MAXLENGTH,
            message = MemberUtilConst.PASSWORD.UNVALID_LENGTH_MESSAGE)
    private String password1;

    @NotEmpty(message = MemberUtilConst.PASSWORD.ISEMPTY_MESSAGE_2)
    @Size(min = MemberUtilConst.PASSWORD.MINLENGTH,
            max = MemberUtilConst.PASSWORD.MAXLENGTH,
            message = MemberUtilConst.PASSWORD.UNVALID_LENGTH_MESSAGE)
    private String password2;

    @NotEmpty(message = MemberUtilConst.EMAIL.ISEMPTY_MESSAGE)
    @Size(max = MemberUtilConst.EMAIL.MAXLENGTH,
            message = MemberUtilConst.EMAIL.UNVALID_LENGTH_MESSAGE)
    @Email(message = MemberUtilConst.EMAIL.UNVALID_PATTERN_MESSAGE)
    private String email;
}
