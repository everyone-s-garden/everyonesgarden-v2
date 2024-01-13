package com.garden.back.testutil.member;

import com.garden.back.member.Member;
import com.garden.back.member.Role;

public class MemberFixture {

    public static Member member(){
        return Member.create(
                "j234534a@naver.com",
                "금쪽이",
                Role.USER
        );
    }
}
