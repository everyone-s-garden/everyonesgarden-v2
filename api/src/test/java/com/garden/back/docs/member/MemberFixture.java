package com.garden.back.docs.member;

import com.garden.back.member.service.dto.MemberMyPageResult;

public class MemberFixture {
    public static MemberMyPageResult memberMyPageResult() {
        return new MemberMyPageResult("불가사리");
    }
}
