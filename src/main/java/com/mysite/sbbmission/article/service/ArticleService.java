package com.mysite.sbbmission.article.service;

import com.mysite.sbbmission.article.dto.ArticleForm;
import com.mysite.sbbmission.article.entity.Article;
import com.mysite.sbbmission.article.repository.ArticleRepository;
import com.mysite.sbbmission.comment.entity.Comment;
import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import com.mysite.sbbmission.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Page<Article> getList(int page, String kw, List<String> kwTypes) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return search(kwTypes, kw, pageable);
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
        // 영속성 컨텍스트는, JPA findById 등 해당 메소드로 불러왔을 때만 사용 가능
        articleRepository.save(article);
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

    public Model addDetailPageHaterLikerIdList(Model model, Article article) {
        model.addAttribute("articleLikerIdList", getArticleLikerIdList(article));
        model.addAttribute("articleHaterIdList", getArticleHaterIdList(article));
        model.addAttribute("commentLikerIdList", getCommentLikerIdList(article));
        model.addAttribute("commentHaterIdList", getCommentHaterIdList(article));
        return model;
    }

    // 제목, 내용, 질문, 게시글 작성자, 답변 작성자
    public Page<Article> search(List<String> kwTypes, String kw, Pageable pageable) {
        return articleRepository.search(kwTypes, kw, pageable);
    }
}
