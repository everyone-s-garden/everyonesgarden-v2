package com.garden.back.member;

import jakarta.persistence.*;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

@Entity
@Getter
@Table(name = "members")
public class Member {

    protected Member() {
    }

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

    private Member(String email, String nickname, Role role) {

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }

        if (StringUtils.length(nickname) > 30) {
            throw new IllegalArgumentException("닉네임은 30글자 이내로 입력해주세요");
        }

        this.mannerScore = 0.0f;
        this.email = email;
        this.role = role;
        this.nickname = nickname;
        this.memberMannerGrade = MemberMannerGrade.SEED;
    }

    public static Member create(String email, String nickname, Role role) {
        return new Member(email, nickname, role);
    }

    public void updateMannerScore(Float mannerScore) {
        this.mannerScore += mannerScore;
        this.memberMannerGrade = MemberMannerGrade.getGradeByScore(mannerScore);
    }

    public void updateProfile(String nickname, String image) {
        this.nickname = nickname;
        this.profileImageUrl = image;
    }
}