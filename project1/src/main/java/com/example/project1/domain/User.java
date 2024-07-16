package com.example.project1.domain;

import com.example.project1.domain.type.EProvider;
import com.example.project1.domain.type.ERole;
import com.example.project1.dto.request.AuthSignUpDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private EProvider provider;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "nickname", length = 12)
    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    /* User Status */
    @Column(name = "is_login", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean isLogin;

    @Column(name = "refresh_token")
    private String refreshToken;

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Diary> diaryList = new ArrayList<>();

    @Builder
    public User(String serialId, String password, String phoneNumber, String nickname, EProvider provider, ERole role) {
        this.serialId = serialId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.provider = provider;
        this.role = role;
        this.createdDate = LocalDate.now();
        this.isLogin = false;
    }


    public User(String serialId, String password, EProvider provider, ERole role) {
        this.serialId = serialId;
        this.password = password;
        this.provider = provider;
        this.role = role;
        this.createdDate = LocalDate.now();
        this.isLogin = true;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateInfo(String nickname, String phoneNumber) {
        if (nickname != null && (!Objects.equals(this.nickname, nickname))) {
            this.nickname = nickname;
        }

        if (phoneNumber != null && (!Objects.equals(this.phoneNumber, phoneNumber))) {
            this.phoneNumber = phoneNumber;
        }
    }

    public static User signUp(AuthSignUpDto authSignUpDto, String encodedPassword) {
        User user = User.builder()
                .serialId(authSignUpDto.serialId())
                .password(encodedPassword)
                .provider(EProvider.DEFAULT)
                .role(ERole.USER)
                .build();
        user.register(authSignUpDto.nickname(), authSignUpDto.phoneNumber());

        return user;
    }

    public void register(String nickname, String phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = ERole.USER;
    }
}
