package com.api.notice.application.command;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Builder
public record NoticeCreateCommand(
        String title,
        String content,
        LocalDate startDate,
        LocalDate endDate,
        List<MultipartFile> attachments,
        String createdBy) {

}