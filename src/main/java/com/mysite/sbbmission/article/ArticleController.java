package com.mysite.sbbmission.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleRepository articleRepository;
    @GetMapping("/list")
    public String showList(Model model){
        List<Article> articleList = this.articleRepository.findAll();
        model.addAttribute("articleList", articleList);
        return "article_list";
    }
}
