package com.mysite.sbbmission.article.repository;

import com.mysite.sbbmission.article.entity.Article;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.mysite.sbbmission.article.entity.QArticle.article;
import static com.mysite.sbbmission.comment.entity.QComment.comment;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    // countdistinct
    //
    @Override
    public Page<Article> search(List<String> kwTypes, String kw, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!kw.isBlank() && !kwTypes.isEmpty()) {
            BooleanBuilder typeBuilder = new BooleanBuilder();

            for (String kwType : kwTypes) {
                switch (kwType) {
                    case "article_title":
                        typeBuilder.or(article.title.containsIgnoreCase(kw));
                        break;
                    case "article_content":
                        typeBuilder.or(article.content.containsIgnoreCase(kw));
                        break;
                    case "comment_content":
                        typeBuilder.or(comment.content.containsIgnoreCase(kw));
                        break;
                    case "article_username":
                        typeBuilder.or(article.author.username.containsIgnoreCase(kw));
                        break;
                    case "comment_username":
                        typeBuilder.or(comment.author.username.containsIgnoreCase(kw));
                        break;
                }
            }

            builder.and(typeBuilder);
        }

        JPAQuery<Article> articlesQuery = jpaQueryFactory
                .select(article)
                .from(article) // join 안하는 이유? : article.commentList와 같이 연관 엔티티에 대한 접근은 JPA가 자동 로딩하므로
                // .leftJoin(comment) : 오히려 조인을 하게 되면 FetchType이 Lazy로 설정되어 있으므로 한 번의 쿼리로 못불러옴

                // left join하면 나가는 쿼리
                  /*
                    select article
                    from Article article
                    left join article.commentList as comment
                    */
                .where(builder);

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(article.getType(), article.getMetadata());
            articlesQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        articlesQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> totalQuery = jpaQueryFactory
                .select(article.count())
                .from(article)
                .where(builder);
        return PageableExecutionUtils.getPage(articlesQuery.fetch(), pageable, totalQuery::fetchOne);
    }
}