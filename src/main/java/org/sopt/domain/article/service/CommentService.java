package org.sopt.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.ArticleException;
import org.sopt.common.exception.CommentException;
import org.sopt.common.exception.MemberException;
import org.sopt.common.response.ErrorCode;
import org.sopt.domain.article.dto.request.CommentCreateRequest;
import org.sopt.domain.article.dto.request.CommentUpdateRequest;
import org.sopt.domain.article.dto.response.CommentListResponse;
import org.sopt.domain.article.dto.response.CommentResponse;
import org.sopt.domain.article.entity.Article;
import org.sopt.domain.article.entity.Comment;
import org.sopt.domain.article.repository.ArticleRepository;
import org.sopt.domain.article.repository.CommentRepository;
import org.sopt.domain.member.entity.Member;
import org.sopt.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse createComment(Long memberId, Long articleId, CommentCreateRequest request) {
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ErrorCode.ARTICLE_NOT_FOUND));

        Comment comment = Comment.create(author, article, request.content());
        commentRepository.save(comment);
        return CommentResponse.of(comment);
    }

    public CommentListResponse readComments(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleIdWithAuthor(articleId);
        return CommentListResponse.of(comments);
    }

    @Transactional
    public CommentResponse updateComment(Long memberId, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getAuthor().getId().equals(memberId))
            throw new CommentException(ErrorCode.COMMENT_EDIT_NOT_ALLOWED);

        comment.updateContent(request.content());
        return CommentResponse.of(comment);
    }

    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));
        if (!comment.getAuthor().getId().equals(memberId))
            throw new CommentException(ErrorCode.COMMENT_EDIT_NOT_ALLOWED);
        comment.delete();
    }
}