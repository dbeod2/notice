package com.api.notice.adapter.payload;

import com.api.notice.domain.dto.NoticeDetailDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record NoticeDetailResponse(
        long id,
        String title,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        long viewCount,
        String createdBy
) {

    public static NoticeDetailResponse from(NoticeDetailDto notice) {
        return new NoticeDetailResponse(notice.id(),
                notice.title(),
                notice.content(),
                notice.createdAt(),
                notice.viewCount(),
                notice.createdBy()
        );
    }
}