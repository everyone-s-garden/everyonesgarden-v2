package com.garden.back.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "manner_score")
    private Float mannerScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_manner_grade")
    private MemberMannerGrade memberMannerGrade;

    @Column(name = "profile_image_url")
    private String profileImageUrl;
}