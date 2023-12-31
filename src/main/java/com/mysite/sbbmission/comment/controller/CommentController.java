package com.mysite.sbbmission.comment.controller;

import com.mysite.sbbmission.article.entity.Article;
import com.mysite.sbbmission.article.service.ArticleService;
import com.mysite.sbbmission.comment.entity.Comment;
import com.mysite.sbbmission.comment.dto.CommentForm;
import com.mysite.sbbmission.comment.service.CommentService;
import com.mysite.sbbmission.member.model.entity.Member;
import com.mysite.sbbmission.member.service.MemberService;
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
    @Valid CommentForm commentForm, BindingResult brs, Principal principal){
        Article article = articleService.getArticle(id);

        if (brs.hasErrors()){
            // 에러 발생 시 article을 model로 다시 실어보낸다
            model.addAttribute("article", article);
            model = articleService.addDetailPageHaterLikerIdList(model, article);
            return "article/article_detail";
        }
        Comment comment = commentService.create(article, commentForm, memberService.getMember(principal.getName()));
        return String.format("redirect:/article/detail/%s#comment_%s", comment.getArticle().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(Model model, @PathVariable("id") Long id, @ModelAttribute("commentForm")
    @Valid CommentForm commentForm, BindingResult brs, Principal principal){
        Comment comment = commentService.getComment(id);

        if (!comment.getAuthor().getSignInId().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다");
        }
        if (brs.hasErrors()){
            // 에러 발생 시 article을 model로 다시 실어보낸다
            model = articleService.addDetailPageHaterLikerIdList(model, comment.getArticle());
            model.addAttribute("article", comment.getArticle());
            // todo : 해당 부분 오류 해결(추천 / 비추천 model attribute 추가 후 발생)
            return "article/article_detail";
        }
        commentService.update(comment, commentForm);
        return String.format("redirect:/article/detail/%s#comment_%s", comment.getArticle().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = commentService.getComment(id);
        if (!comment.getAuthor().getSignInId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        commentService.delete(comment);
        return String.format("redirect:/article/detail/%s", comment.getArticle().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String like(Principal principal, @PathVariable("id") Long id) {
        Comment comment = commentService.getComment(id);
        Member member = memberService.getMember(principal.getName());
        commentService.addLike(comment, member);
        return String.format("redirect:/article/detail/%s#comment_%s", comment.getArticle().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/resetlike/{id}")
    public String resetLike(Principal principal, @PathVariable("id") Long id) {
        Comment comment = commentService.getComment(id);
        Member member = memberService.getMember(principal.getName());
        commentService.removeLike(comment, member);
        return String.format("redirect:/article/detail/%s#comment_%s", comment.getArticle().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hate/{id}")
    public String hate(Principal principal, @PathVariable("id") Long id) {
        Comment comment = commentService.getComment(id);
        Member member = memberService.getMember(principal.getName());
        commentService.addHate(comment, member);
        return String.format("redirect:/article/detail/%s#comment_%s", comment.getArticle().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/resethate/{id}")
    public String resetHate(Principal principal, @PathVariable("id") Long id) {
        Comment comment = commentService.getComment(id);
        Member member = memberService.getMember(principal.getName());
        commentService.removeHate(comment, member);
        return String.format("redirect:/article/detail/%s#comment_%s", comment.getArticle().getId(), comment.getId());
    }
}
