package com.garden.back.auth.jwt;

import com.garden.back.member.Member;

import java.util.Date;

public record RefreshToken(
    String key,
    Member member,
    Date expiredDate
) {}
