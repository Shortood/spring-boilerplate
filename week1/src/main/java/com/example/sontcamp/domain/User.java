package com.example.sontcamp.domain;

import com.example.sontcamp.domain.type.EProvider;
import com.example.sontcamp.domain.type.ERole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Getter
@DynamicUpdate
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "social_id", nullable = false, unique = true)
    private String serialId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider loginProvider;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "nickname", length = 12)
    private String nickname;

    @Builder
    public User(String serialId, String password, EProvider loginProvider, ERole role, String nickname) {
        this.serialId = serialId;
        this.password = password;
        this.loginProvider = loginProvider;
        this.role = role;
        this.nickname = nickname;
        this.createdAt= LocalDate.now();
    }
}
