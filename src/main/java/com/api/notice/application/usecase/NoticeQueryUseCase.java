package com.api.notice.application.usecase;

import com.api.notice.domain.dto.NoticeDetailDto;

public interface NoticeQueryUseCase {
    NoticeDetailDto getDetail(final long notice_id);
}
