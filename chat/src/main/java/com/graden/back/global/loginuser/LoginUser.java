package com.graden.back.global.loginuser;

public record LoginUser(
    Long memberId
) {
    public static LoginUser from(MemberInfo memberInfo) {
        return new LoginUser(memberInfo.getMemberId());
    }
}
