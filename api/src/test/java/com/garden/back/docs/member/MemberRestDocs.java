package com.garden.back.docs.member;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.member.MemberController;
import com.garden.back.member.service.MemberService;
import com.garden.back.member.service.dto.MemberMyPageResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberRestDocs extends RestDocsSupport {

    MemberService memberService = mock(MemberService.class);

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }

    @DisplayName("마이페이지를 조회하면 사용자 정보르 얻을 수 있다.")
    @Test
    void getMyPage() throws Exception {
        MemberMyPageResult memberMyPageResult = MemberFixture.memberMyPageResult();
        given(memberService.getMyMember(any())).willReturn(memberMyPageResult);

        mockMvc.perform(get("/members/my"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-my-page-memberInfo",
                        responseFields(
                                fieldWithPath("nickname").type(STRING).description("사용자의 별명")
                        )));
    }

}
