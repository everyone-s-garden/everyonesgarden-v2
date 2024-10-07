package com.garden.back.docs.member;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.member.MemberController;
import com.garden.back.member.MemberMannerGrade;
import com.garden.back.member.dto.UpdateMyProfileRequest;
import com.garden.back.member.repository.response.MemberInfoResponse;
import com.garden.back.member.service.MemberService;
import com.garden.back.member.service.dto.MemberMyPageResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberRestDocsTest extends RestDocsSupport {

    MemberService memberService = mock(MemberService.class);

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }

    @DisplayName("마이페이지를 조회하면 사용자 정보를 얻을 수 있다.")
    @Test
    void getMyPage() throws Exception {
        MemberMyPageResult memberMyPageResult = MemberFixture.memberMyPageResult();
        given(memberService.getMyMember(any())).willReturn(memberMyPageResult);

        mockMvc.perform(get("/members/my"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("get-my-page-memberInfo",
                responseFields(
                    fieldWithPath("nickname").type(STRING).description("사용자의 별명"),
                    fieldWithPath("profileImage").type(STRING).description("사용자의 프로필 이미지"),
                    fieldWithPath("memberMannerGrade").type(STRING).description("사용자의 매너 등급"),
                    fieldWithPath("email").type(STRING).description("사용자의 email")
                )));
    }

    @DisplayName("내 프로필 변경")
    @Test
    void updateMyProfile() throws Exception {
        doNothing().when(memberService).updateProfile(any());
        MockMultipartFile firstImage = new MockMultipartFile(
            "image",
            "image.png",
            "image/png",
            "image-file".getBytes()
        );

        UpdateMyProfileRequest request = new UpdateMyProfileRequest("아무개123");

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "texts",
            "content",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(
                multipart("/members/my")
                    .file(firstImage)
                    .file(mockMultipartFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(req -> {
                        req.setMethod("PATCH");
                        return req;
                    })
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("update-member-profile",
                requestPartFields("texts",
                    fieldWithPath("nickname").type(STRING).description("별명")
                ),
                requestParts(
                    partWithName("texts").description("이미지 외 문자 값들"),
                    partWithName("image").description("프로필 이미지").optional()
                )
            ));
    }

    @DisplayName("사용자의 정보를 id로 조회한다.")
    @Test
    void findMemberById() throws Exception {
        given(memberService.findMemberById(any())).willReturn(new MemberInfoResponse("닉네임", "이미지 url", MemberMannerGrade.SEED, "email"));
        mockMvc.perform(get("/members/{id}", 1L))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("findMemberById",
                pathParameters(
                    parameterWithName("id").description("회원 아이디")
                ),
                responseFields(
                    fieldWithPath("nickname").type(STRING).description("사용자의 별명"),
                    fieldWithPath("profileImageUrl").type(STRING).description("사용자의 프로필 이미지"),
                    fieldWithPath("memberMannerGrade").type(STRING).description("사용자의 매너 등급"),
                    fieldWithPath("email").type(STRING).description("사용자의 email")
                )));
    }
}
