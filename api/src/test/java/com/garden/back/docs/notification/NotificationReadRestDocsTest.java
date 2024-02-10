package com.garden.back.docs.notification;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.notification.NotificationReadApplication;
import com.garden.back.notification.NotificationReadController;
import com.garden.back.notification.domain.Notification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificationReadRestDocsTest extends RestDocsSupport {
    NotificationReadApplication notificationReadApplication = mock(NotificationReadApplication.class);

    @Override
    protected Object initController() {
        return new NotificationReadController(notificationReadApplication);
    }

    @DisplayName("알람을 읽음 처리를 한다.")
    @Test
    void markNewNotificationsAsRead() throws Exception {
        given(notificationReadApplication.markAsRead(anyLong(), anyLong())).willReturn(new Notification(1L, "title", "summary", "content", "recipient", false));

        mockMvc.perform(patch("/notification/{notificationId}/mark-as-read", 1L))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("mark-as-read-unread-notification",
                pathParameters(
                    parameterWithName("notificationId").description("읽음 처리 할 알람의 id")
                ),
                responseFields(
                    fieldWithPath("title").type(STRING).description("제목"),
                    fieldWithPath("content").type(STRING).description("내용"),
                    fieldWithPath("isRead").type(BOOLEAN).description("읽음 여부")
                )
            ));
    }
}
