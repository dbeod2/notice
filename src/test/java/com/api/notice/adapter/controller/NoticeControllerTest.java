package com.api.notice.adapter.controller;

import com.api.notice.application.usecase.*;
import com.api.notice.domain.dto.NoticeDetailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoticeController.class)
@AutoConfigureRestDocs
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoticeQueryUseCase noticeQueryUseCase;
    @MockitoBean
    private NoticeUseCase noticeUseCase;

    @Test
    @DisplayName("공지사항 상세 조회")
    void getNotice() throws Exception {
        // given
        NoticeDetailDto detailDto = new NoticeDetailDto(
                1L,
                "공지사항 제목",
                "공지사항 내용",
                LocalDateTime.now(),
                10,
                "admin"
        );
        given(noticeQueryUseCase.getDetail(1L)).willReturn(detailDto);

        // when & then
        mockMvc.perform(get("/api/v1/notices/{noticeId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("notice-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("noticeId").description("공지사항 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("공지사항 ID"),
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("createdAt").description("등록일시"),
                                fieldWithPath("viewCount").description("조회수"),
                                fieldWithPath("createdBy").description("작성자")
                        )
                ));
    }
    @Test
    @DisplayName("공지사항 등록")
    void createNotice() throws Exception {
        MockMultipartFile notice = new MockMultipartFile(
                "notice",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                ("""
                        {
                          "title": "공지사항 제목",
                          "content": "공지사항 내용",
                          "startDate": "2024-12-01",
                          "endDate": "2024-12-31",
                          "createdBy": "admin"
                        }""").getBytes()
        );

        MockMultipartFile file = new MockMultipartFile(
                "attachments", "file1.jpg", MediaType.APPLICATION_PDF_VALUE, "testjpg".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/notices")
                        .file(notice)
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("notice-create",
                        requestParts(
                                partWithName("notice").description("공지사항 JSON 데이터"),
                                partWithName("attachments").description("첨부 파일들")
                        )));
    }

    @Test
    @DisplayName("공지사항 수정")
    void updateNotice() throws Exception {
        mockMvc.perform(put("/api/v1/notices/{noticeId}", 1L)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content("""
                                {
                                  "title": "수정된 공지사항 제목",
                                  "content": "수정된 공지사항 내용",
                                  "attachments": ["/uploads/update.jpg"]
                                }""")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("notice-update",
                        pathParameters(
                                parameterWithName("noticeId").description("수정할 공지사항 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("공지사항 제목"),
                                fieldWithPath("content").description("공지사항 내용"),
                                fieldWithPath("attachments").description("첨부 파일들")
                        )));
    }
    @Test
    @DisplayName("공지사항 삭제")
    void deleteNotice() throws Exception {

        // when & then
        mockMvc.perform(delete("/api/v1/notices/{noticeId}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("notice-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("noticeId").description("삭제할 공지사항 ID")
                        )
                ));
    }
}