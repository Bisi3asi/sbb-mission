package com.mysite.sbbmission.article;

import com.mysite.sbbmission.comment.CommentForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String showList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Article> paging = articleService.getList(page);

        model.addAttribute("paging", paging);
        return "article/article_list";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);

        model.addAttribute("article", article);
        model.addAttribute("commentForm", new CommentForm());
        return "article/article_detail";
    }

    @GetMapping("/write")
    public String showWriteForm(ArticleForm articleForm) {
        return "article/article_form";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute("articleForm")
                        @Valid ArticleForm articleForm, BindingResult brs, Principal principal) {
        // Valid : ArticleForm(NotEmpty 등 작동), BindingResult(검증 작동)
        // BindingResult : 폼 객체를 도메인에 바인딩, 오류를 로깅해 저장
        // RequestParam : 매개변수와 폼을 바인딩, Spring은 요청 파라미터와 필드 이름 동일 시 @RequestParam 생략 가능
        // Principal : Spring Security에서 로그인한 사용자 정보 확인
        if (brs.hasErrors()) {
            return "article/article_form";
        }
        articleService.create(articleForm.getTitle(), articleForm.getContent(),
                memberService.getMember(principal.getName()));
        return "redirect:/article/list";
    }
}
