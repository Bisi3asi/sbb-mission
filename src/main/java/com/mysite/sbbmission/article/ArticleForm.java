package com.mysite.sbbmission.article;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ArticleForm {
    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max = 50, message = "제목은 50자를 초과할 수 없습니다.")
    private String title;

    @NotEmpty(message="내용은 필수항목입니다.")
    @Size(max = 500, message = "내용은 500자를 초과할 수 없습니다.")
    private String content;
}
