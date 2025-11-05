package org.sopt.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.MemberException;
import org.sopt.common.response.ErrorCode;
import org.sopt.domain.article.dto.request.ArticleCreateRequestDTO;
import org.sopt.domain.article.dto.response.ArticleResponseDTO;
import org.sopt.domain.article.entity.Article;
import org.sopt.domain.article.repository.ArticleRepository;
import org.sopt.domain.member.entity.Member;
import org.sopt.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ArticleResponseDTO createArticle(ArticleCreateRequestDTO requestDTO) {
        Member member = memberRepository.findById(requestDTO.memberId())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        Article article = requestDTO.toEntity();
        member.addArticle(article);
        articleRepository.save(article);

        return ArticleResponseDTO.of(article);
    }
}