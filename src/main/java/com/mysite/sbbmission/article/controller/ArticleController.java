package com.mysite.sbbmission.article.controller;

import com.mysite.sbbmission.article.dto.ArticleForm;
import com.mysite.sbbmission.article.entity.Article;
import com.mysite.sbbmission.article.service.ArticleService;
import com.mysite.sbbmission.comment.dto.CommentForm;
import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import com.mysite.sbbmission.member.model.entity.Member;
import com.mysite.sbbmission.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String showList(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "") String kw,
                           @RequestParam(value="kwType", defaultValue = "") List<String> kwTypes) {
        if (page < 0) {
            throw new DataNotFoundException("존재하지 않는 페이지입니다.");
        }

        Map<String, Boolean> kwTypesMap = kwTypes
                .stream()
                .collect(Collectors.toMap(
                        kwType -> kwType,
                        kwType -> true
                ));

        Page<Article> paging = articleService.getList(page, kw, kwTypes);
        System.out.println("Total Elements: " + paging.getTotalElements());
        System.out.println("Total Pages: " + paging.getTotalPages());
        System.out.println("Number: " + paging.getNumber());
        System.out.println("Size: " + paging.getSize());
        System.out.println("Content: " + paging.getContent());

        if (page > paging.getTotalPages() - 1) {
            throw new DataNotFoundException("검색 결과가 존재하지 않습니다.");
        }

        model.addAttribute("kwTypesMap", kwTypesMap);
        model.addAttribute("paging", paging);
        return "article/article_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);

        // 추천, 비추천자 view 전달
        model = articleService.addDetailPageHaterLikerIdList(model, article);
        model.addAttribute("article", article);
        model.addAttribute("commentForm", new CommentForm());
        return "article/article_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWriteForm(ArticleForm articleForm) {
        return "article/article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@ModelAttribute("articleForm") @Valid ArticleForm articleForm,
                        BindingResult brs, Principal principal) {
        // @ModelAttribute : 스프링 2 -> 3으로 버전 업되며 붙이지 않아도 되지만 Intellij IDEA 내 html 컴파일 에러로 인식
        // Valid : ArticleForm(NotEmpty 등 작동), BindingResult(검증 작동)
        // BindingResult : 폼 객체를 도메인에 바인딩, 오류를 로깅해 저장
        // RequestParam : 매개변수와 폼을 바인딩, Spring은 요청 파라미터와 필드 이름 동일 시 @RequestParam 생략 가능
        // Principal : Spring Security에서 로그인한 사용자 정보 확인
        if (brs.hasErrors()) {
            return "article/article_form";
        }
        articleService.create(articleForm, memberService.getMember(principal.getName()));
        return "redirect:/article/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String showModifyForm(ArticleForm articleForm, @PathVariable("id") Long id, Principal principal) {
        Article article = articleService.getArticle(id);
        if (!article.getAuthor().getSignInId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        return "article/article_modify_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@ModelAttribute("articleForm") @Valid ArticleForm articleForm, BindingResult brs,
                         @PathVariable("id") Long id, Principal principal) {
        Article article = articleService.getArticle(id);
        if (!article.getAuthor().getSignInId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        if (brs.hasErrors()) {
            return "article/article_modify_form";
        }
        articleService.update(article, articleForm);
        return String.format("redirect:/article/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);
        if (!article.getAuthor().getSignInId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        articleService.delete(article);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String like(Principal principal, @PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);
        Member member = memberService.getMember(principal.getName());
        articleService.addLike(article, member);
        return String.format("redirect:/article/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/resetlike/{id}")
    public String resetLike(Principal principal, @PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);
        Member member = memberService.getMember(principal.getName());
        articleService.removeLike(article, member);
        return String.format("redirect:/article/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hate/{id}")
    public String hate(Principal principal, @PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);
        Member member = memberService.getMember(principal.getName());
        articleService.addHate(article, member);
        return String.format("redirect:/article/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/resethate/{id}")
    public String resetHate(Principal principal, @PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);
        Member member = memberService.getMember(principal.getName());
        articleService.removeHate(article, member);
        return String.format("redirect:/article/detail/%s", id);
    }
}
