package com.mysite.sbbmission.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {
    @NotBlank(message = "글 제목을 작성해주세요.")
    @Size(max = 50, message = "50자 미만으로 작성해주세요!")
    private String title;

    @NotBlank(message = "글 내용을 작성해주세요.")
    @Size(max = 500, message = "500자 미만으로 작성해주세요!")
    private String content;
}
