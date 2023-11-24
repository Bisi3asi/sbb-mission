package com.mysite.sbbmission.comment;

import com.mysite.sbbmission.article.Article;
import com.mysite.sbbmission.article.ArticleService;
import com.mysite.sbbmission.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write/{id}")
    public String write(Model model, @PathVariable("id") Long id, @ModelAttribute("commentForm")
    @Valid CommentForm commentform, BindingResult brs, Principal principal){
        Article article = articleService.getArticle(id);

        if (brs.hasErrors()){
            // 에러 발생 시 article을 model로 다시 실어보낸다
            model.addAttribute("article", article);
            return "article/article_detail";
        }
        commentService.create(article, commentform.getContent(), memberService.getMember(principal.getName()));
        return String.format("redirect:/article/detail/%s", id);
    }
}
