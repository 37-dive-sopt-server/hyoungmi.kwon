package org.sopt.domain.article.entity;

import jakarta.persistence.*;
import org.sopt.domain.member.entity.Member;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag", nullable = false)
    private ArticleTag tag;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}