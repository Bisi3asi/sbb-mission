package com.mysite.sbbmission.comment;

import com.mysite.sbbmission.article.Article;
import com.mysite.sbbmission.article.ArticleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;

    @PostMapping("/write/{id}")
    public String write(Model model, @PathVariable("id") Long id,
                        @ModelAttribute("commentForm") @Valid CommentForm commentform, BindingResult brs){
        Article article = articleService.getArticle(id);

        if (brs.hasErrors()){
            // 에러 발생 시 article을 model로 다시 실어보낸다
            model.addAttribute("article", article);
            return "article/article_detail";
        }
        commentService.create(article, commentform.getContent());
        return String.format("redirect:/article/detail/%s", id);
    }
}
