package com.api.notice.domain.dto;

import com.api.notice.domain.entity.Notice;

import java.time.LocalDateTime;

public record NoticeDetailDto(
        long id,
        String title,
        String content,
        LocalDateTime createdAt,
        int viewCount,
        String createdBy) {

    public static NoticeDetailDto from(final Notice notice) {
        return new NoticeDetailDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedAt(),
                notice.getViewCount(),
                notice.getCreatedBy());
    }
}
