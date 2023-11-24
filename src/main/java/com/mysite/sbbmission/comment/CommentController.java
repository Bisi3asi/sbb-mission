package com.mysite.sbbmission.comment;

import com.mysite.sbbmission.article.Article;
import com.mysite.sbbmission.article.ArticleService;
import com.mysite.sbbmission.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String showModifyForm(CommentForm commentForm, @PathVariable("id") long id, Principal principal){
        Comment comment = commentService.getComment(id);
        if (!comment.getAuthor().getSignInId().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다");
        }
        commentForm.setContent(comment.getContent());
        // todo : modify PostMapping으로 별도로 js로 view에서 바로 수정가능하게끔 작업
        return "comment_form";
    }
}
