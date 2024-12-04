package com.api.notice.application.service;

import com.api.notice.application.usecase.NoticeQueryUseCase;
import com.api.notice.infrastructure.persistence.NoticeRepository;
import com.api.notice.domain.dto.NoticeDetailDto;
import com.api.notice.domain.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeQueryService implements NoticeQueryUseCase {

    private final NoticeRepository noticeRepository;

    @Override
    public NoticeDetailDto getDetail(final long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("공지사항 번호가 없습니다."));

        notice.increaseViewCount();

        return NoticeDetailDto.from(notice);
    }
}
