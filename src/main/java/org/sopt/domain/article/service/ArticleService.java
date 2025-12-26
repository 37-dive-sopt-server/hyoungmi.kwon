package org.sopt.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.ArticleException;
import org.sopt.common.exception.MemberException;
import org.sopt.common.response.ErrorCode;
import org.sopt.domain.article.dto.request.ArticleCreateRequest;
import org.sopt.domain.article.dto.response.ArticleResponse;
import org.sopt.domain.article.entity.Article;
import org.sopt.domain.article.repository.ArticleRepository;
import org.sopt.domain.member.entity.Member;
import org.sopt.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ArticleResponse createArticle(ArticleCreateRequest requestDTO) {
        Member member = memberRepository.findById(requestDTO.memberId())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        if (articleRepository.existsByTitle(requestDTO.title())) {
            throw new ArticleException(ErrorCode.DUPLICATE_ARTICLE_TITLE);
        }

        Article article = requestDTO.toEntity();
        member.addArticle(article);
        articleRepository.save(article);

        return ArticleResponse.of(article);
    }

    public ArticleResponse getArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ErrorCode.ARTICLE_NOT_FOUND));
        return ArticleResponse.of(article);
    }

    public List<ArticleResponse> getArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(ArticleResponse::of)
                .collect(Collectors.toList());
    }
}