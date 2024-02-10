package com.garden.back.docs.notification;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.notification.NotificationGetApplication;
import com.garden.back.notification.NotificationGetController;
import com.garden.back.notification.domain.Notification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.fasterxml.jackson.databind.node.JsonNodeType.NUMBER;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificationRestDocsTest extends RestDocsSupport {
    NotificationGetApplication notificationGetApplication = mock(NotificationGetApplication.class);

    @Override
    protected Object initController() {
        return new NotificationGetController(notificationGetApplication);
    }

    @DisplayName("모든 알림 조회 API 테스트")
    @Test
    void getAllNotifications() throws Exception {
        given(notificationGetApplication.getAll(anyLong())).willReturn(List.of(new Notification(1L, "알림 내용 1", "summary", "content", "recipient", false)));

        mockMvc.perform(get("/notification/all"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("notification-get-all",
                responseFields(
                    fieldWithPath("[].title").type(STRING).description("알림의 제목"),
                    fieldWithPath("[].content").type(STRING).description("알림의 내용"),
                    fieldWithPath("[].isRead").type(BOOLEAN).description("알림 읽음 여부")
                )));
    }

    @DisplayName("읽지 않은 알림 수 폴링 API 테스트")
    @Test
    void pollUnreadNotificationsCount() throws Exception {
        // given
        given(notificationGetApplication.pollUnreadCount(anyLong())).willReturn(1);

        // when & then
        mockMvc.perform(get("/notification/new/poll"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("notification-poll-unread",
                responseFields(
                    fieldWithPath("count").type(NUMBER).description("읽지 않은 알림의 수")
                )));
    }


    @DisplayName("읽지 않은 알림 조회 API 테스트")
    @Test
    void getNewNotificationsTest() throws Exception {
        given(notificationGetApplication.getAllUnread(anyLong())).willReturn(List.of(new Notification(1L, "새 알림 1", "요약 1", "내용 1", new Object(), false)));

        mockMvc.perform(get("/notification/new"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("notification-get-new",
                responseFields(
                    fieldWithPath("[].title").type(STRING).description("알림의 제목"),
                    fieldWithPath("[].content").type(STRING).description("알림의 내용"),
                    fieldWithPath("[].isRead").type(BOOLEAN).description("알림 읽음 여부")
                )));
    }

}
