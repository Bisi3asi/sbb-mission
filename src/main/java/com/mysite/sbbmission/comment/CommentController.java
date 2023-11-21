package com.mysite.sbbmission.comment;

import com.mysite.sbbmission.article.Article;
import com.mysite.sbbmission.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;

    @PostMapping("/create/{id}")
    public String post(Model model, @PathVariable("id") Long id, @RequestParam String content){
        Article article = articleService.getArticle(id);
        commentService.create(article, content);
        model.addAttribute("article", article);
        return String.format("redirect:/article/detail/%s", id);
    }
}
