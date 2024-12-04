package com.api.notice.application.service;

import com.api.notice.application.command.NoticeCreateCommand;
import com.api.notice.application.command.NoticeUpdateCommand;
import com.api.notice.domain.entity.Notice;
import com.api.notice.domain.entity.NoticeAttachment;
import com.api.notice.infrastructure.file.FileService;
import com.api.notice.infrastructure.persistence.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoticeCommandServiceTest {

    @InjectMocks
    private NoticeCommandService noticeCommandService;

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private FileService fileService;

    @Test
    @DisplayName("공지사항 생성 - 성공")
    void createNotice_Success() {
        // given
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.jpg",
            MediaType.TEXT_PLAIN_VALUE,
            "test content".getBytes()
        );

        NoticeCreateCommand command = NoticeCreateCommand.builder()
            .title("테스트 제목")
            .content("테스트 내용")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(7))
            .createdBy("admin")
            .attachments(List.of(file))
            .build();

        given(fileService.upload(any())).willReturn("/uploads/test.jpg");
        given(noticeRepository.save(any(Notice.class))).willReturn(new Notice());

        // when
        noticeCommandService.create(command);

        // then
        verify(fileService, times(1)).upload(any());
        verify(noticeRepository, times(1)).save(any(Notice.class));
    }

    @Test
    @DisplayName("공지사항 수정 - 성공")
    void updateNotice_Success() {
        Notice existingNotice = Notice.builder()
                .id(1L)
                .title("원본 제목")
                .content("원본 내용")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .createdBy("admin")
                .build();

        NoticeAttachment existingAttachment = new NoticeAttachment(
                "/uploads/original.jpg",
                "original.jpg"
        );
        existingNotice.addAttachment(existingAttachment);

        // 수정할 데이터
        MockMultipartFile newFile = new MockMultipartFile(
                "file",
                "updated.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "updated content".getBytes()
        );

        NoticeUpdateCommand command = NoticeUpdateCommand.builder()
                .noticeId(1L)
                .title("수정된 제목")
                .content("수정된 내용")
                .attachments(List.of(newFile))
                .build();

        given(noticeRepository.findById(1L)).willReturn(Optional.of(existingNotice));
        given(fileService.upload(any())).willReturn("/uploads/updated.jpg");

        // when
        noticeCommandService.update(command);

        // then
        verify(noticeRepository).findById(1L);
        verify(fileService).deleteFile("/uploads/original.jpg");
        verify(fileService).upload(any());
    }

    @Test
    @DisplayName("존재하지 않는 공지사항 수정 시 예외 발생")
    void updateNotice_NotFound() {
        // given
        NoticeUpdateCommand command = NoticeUpdateCommand.builder()
            .noticeId(999L)
            .title("수정된 제목")
            .content("수정된 내용")
            .build();

        given(noticeRepository.findById(999L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> noticeCommandService.update(command))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("공지사항 삭제 - 성공")
    void deleteNotice_Success() {
        // given
        Notice notice = Notice.builder()
            .id(1L)
            .title("제목")
            .content("내용")
            .build();

        given(noticeRepository.findById(1L)).willReturn(Optional.of(notice));

        // when
        noticeCommandService.delete(1L);

        // then
        verify(noticeRepository, times(1)).delete(notice);
    }
}