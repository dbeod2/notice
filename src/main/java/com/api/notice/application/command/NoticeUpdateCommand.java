package com.api.notice.application.command;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record NoticeUpdateCommand(
        long noticeId,
        String title,
        String content,
        List<MultipartFile> attachments) {
}
