package com.graden.back.auth.client;

import com.garden.back.member.Member;

public interface MemberProvider {
    Member getMember(String authorization);
}
