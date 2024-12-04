package com.api.notice.application.service;

import com.api.notice.domain.dto.NoticeDetailDto;
import com.api.notice.domain.entity.Notice;
import com.api.notice.domain.exception.NoticeException;
import com.api.notice.infrastructure.persistence.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoticeQueryServiceTest {

    @InjectMocks
    private NoticeQueryService noticeQueryService;

    @Mock
    private NoticeRepository noticeRepository;

    @Test
    @DisplayName("공지사항 상세 조회 - 성공")
    void getNoticeDetail_Success() {
        // given
        Notice notice = Notice.builder()
            .id(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .createdBy("admin")
            .viewCount(0)
            .build();

        given(noticeRepository.findById(1L)).willReturn(Optional.of(notice));

        // when
        NoticeDetailDto result = noticeQueryService.getDetail(1L);

        // then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("테스트 제목");
        assertThat(result.content()).isEqualTo("테스트 내용");
        assertThat(result.createdBy()).isEqualTo("admin");
        verify(noticeRepository).findById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 공지사항 조회 시 예외 발생")
    void getNoticeDetail_NotFound() {
        // given
        given(noticeRepository.findById(999L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> noticeQueryService.getDetail(999L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("공지사항 조회 시 조회수 증가")
    void getNoticeDetail_IncreaseViewCount() {
        // given
        Notice notice = Notice.builder()
            .id(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .createdBy("admin")
            .viewCount(0)
            .build();

        given(noticeRepository.findById(1L)).willReturn(Optional.of(notice));

        // when
        noticeQueryService.getDetail(1L);

        // then
        assertThat(notice.getViewCount()).isEqualTo(1);
    }
} 