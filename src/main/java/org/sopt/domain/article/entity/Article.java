package org.sopt.domain.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.domain.member.entity.Member;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
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

    public Article(String title, String content, ArticleTag tag) {
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public static Article create(String title, String content, ArticleTag tag) {
        return new Article(title, content, tag);
    }

    public void connectMember(Member member) {
        this.member = member;
    }
}