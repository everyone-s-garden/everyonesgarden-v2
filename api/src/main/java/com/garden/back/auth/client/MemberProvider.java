package com.garden.back.auth.client;

import com.garden.back.member.Member;

public interface MemberProvider {
    Member getMember(AuthRequest authorization);
}
