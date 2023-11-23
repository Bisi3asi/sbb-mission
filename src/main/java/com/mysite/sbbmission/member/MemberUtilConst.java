package com.mysite.sbbmission.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberUtilConst {
    ;
    public static class SIGNIN_ID {

        public static final int MINLENGTH = 8;
        public static final int MAXLENGTH = 20;
        public static final String UNVALID_LENGTH_MESSAGE = "ID는 8자 이상, 20자 이하로 설정해주세요.";
        public static final String ISEMPTY_MESSAGE = "ID는 필수항목입니다.";
        public static final String UNUNIQUE_MESSAGE = "이미 가입된 ID입니다.";
    }

    public static class PASSWORD {
        public static final int MINLENGTH = 8;
        public static final int MAXLENGTH = 20;
        public static final String UNVALID_LENGTH_MESSAGE = "비밀번호는 8자 이상, 20자 이하로 설정해주세요.";
        public static final String ISEMPTY_MESSAGE = "비밀번호는 필수항목입니다.";
        public static final String ISEMPTY_MESSAGE_2 = "비밀번호 확인은 필수항목입니다.";
        public static final String PW1PW2_UNMATCH = "2개의 패스워드가 일치하지 않습니다.";
    }

    public static class USERNAME {
        public static final int MINLENGTH = 2;
        public static final int MAXLENGTH = 10;
        public static final String UNVALID_LENGTH_MESSAGE = "이름은 2자 이상, 10자 이하로 설정해주세요.";
        public static final String ISEMPTY_MESSAGE = "이름은 필수항목입니다.";
        public static final String UNUNIQUE_MESSAGE = "이미 사용중인 회원 이름입니다.";
    }

    public static class EMAIL {
        public static final int MAXLENGTH = 40;
        public static final String UNVALID_LENGTH_MESSAGE = "이메일은 40자 이하로 설정해주세요.";
        public static final String UNVALID_PATTERN_MESSAGE = "이메일 형식을 확인해주세요.";
        public static final String ISEMPTY_MESSAGE = "이메일은 필수항목입니다.";
        public static final String UNUNIQUE_MESSAGE = "이미 사용중인 이메일입니다.";
    }
}
