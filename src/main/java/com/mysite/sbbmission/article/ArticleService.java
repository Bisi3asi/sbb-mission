package com.mysite.sbbmission.article;

import com.mysite.sbbmission.comment.Comment;
import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import com.mysite.sbbmission.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Page<Article> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    public Article getArticle(Long id) {
        Optional<Article> article = this.articleRepository.findById(id);

        if (article.isPresent()) {
            return article.get();
        } else {
            throw new DataNotFoundException("존재하지 않는 게시물입니다.");
        }
    }

    @Transactional
    public void create(ArticleForm articleForm, Member author) {
        Article article = Article.builder()
                .title(articleForm.getTitle().trim())
                .content(articleForm.getContent().trim())
                .author(author)
                .build();
        articleRepository.save(article);
    }

    @Transactional
    public void update(Article article, ArticleForm articleForm) {
        article = article.toBuilder()
                .title(articleForm.getTitle())
                .content(articleForm.getContent())
                .build();
    }

    @Transactional
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Transactional
    public void addLike(Article article, Member member) {
        article.getLiker().add(member);
    }

    @Transactional
    public void removeLike(Article article, Member member) {
        article.getLiker().remove(member);
    }

    @Transactional
    public void addHate(Article article, Member member) {
        article.getHater().add(member);
    }

    @Transactional
    public void removeHate(Article article, Member member) {
        article.getHater().remove(member);
    }

    // 게시글을 추천한 사용자 id 리스트를 리턴
    public List<String> getArticleLikerIdList(Article article) {
        return article.getLiker().stream()
                .map(Member::getSignInId)
                .collect(Collectors.toList());
    }

    public List<String> getArticleHaterIdList(Article article) {
        return article.getHater().stream()
                .map(Member::getSignInId)
                .collect(Collectors.toList());
    }

    // 게시글의 코멘트를 추천한 사용자 id 리스트를 리턴
    public Map<Long, List<String>> getCommentLikerIdList(Article article) {
        return article.getCommentList().stream()
                .collect(Collectors.toMap(
                        Comment::getId,
                        comment -> comment.getLiker().stream()
                                .map(Member::getSignInId)
                                .collect(Collectors.toList())
                ));
    }
    public Map<Long, List<String>> getCommentHaterIdList(Article article) {
        return article.getCommentList().stream()
                .collect(Collectors.toMap(
                        Comment::getId,
                        comment -> comment.getHater().stream()
                                .map(Member::getSignInId)
                                .collect(Collectors.toList())
                ));
    }

    public Model addDetailPageHaterLikerIdList(Model model, Article article){
        model.addAttribute("articleLikerIdList", getArticleLikerIdList(article));
        model.addAttribute("articleHaterIdList", getArticleHaterIdList(article));
        model.addAttribute("commentLikerIdList", getCommentLikerIdList(article));
        model.addAttribute("commentHaterIdList", getCommentHaterIdList(article));
        return model;
    }

}
