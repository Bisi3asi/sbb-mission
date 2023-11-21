package com.mysite.sbbmission.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String showWriteForm(){
        return "article/article_form";
    }
    @PostMapping("/write")
    public String write(@RequestParam String title, @RequestParam String content){
        articleService.create(title, content);
        return "redirect:/article/list";
    }
}
