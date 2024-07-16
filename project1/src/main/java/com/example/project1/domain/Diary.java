package com.example.project1.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Getter
@DynamicUpdate
@Table(name = "diaries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    /* Relation Child Mapping */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Diary(String title, String content, User user, LocalDate createdDate) {
        this.createdDate = createdDate;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
