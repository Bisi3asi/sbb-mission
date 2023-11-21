package com.mysite.sbbmission.article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/list")
    public String showList(Model model){
        List<Article> articleList = articleService.getList();
        model.addAttribute("articleList", articleList);
        return "article/article_list";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable("id") Long id){
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article/article_detail";
    }

    @GetMapping("/write")
    public String showWriteForm(Model model) {
        model.addAttribute("articleForm", new ArticleForm());
        return "article/article_form";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute("articleForm") @Valid ArticleForm articleForm, BindingResult brs){
        // Valid : ArticleForm(NotEmpty 등 작동), BindingResult(검증 작동)
        // BindingResult : 폼 객체를 도메인에 바인딩, 오류를 로깅해 저장
        // RequestParam : 매개변수와 폼을 바인딩, Spring은 요청 파라미터와 필드 이름 동일 시 @RequestParam 생략 가능
        if (brs.hasErrors()){
            return "article/article_form";
        }
        articleService.create(articleForm.getTitle(), articleForm.getContent());
        return "redirect:/article/list";
    }
}
