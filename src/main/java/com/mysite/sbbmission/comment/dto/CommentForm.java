package com.mysite.sbbmission.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotBlank(message = "댓글 내용을 작성해주세요.")
    @Size(max = 300, message = "댓글은 300자 미만으로 작성이 가능합니다.")
    private String content;
}
