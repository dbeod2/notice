package com.api.notice.adapter.payload;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record NoticeCreateRequest(
        @NotBlank
        String title,
        @NotBlank
        String content,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate,
        List<MultipartFile> attachments,
        @NotBlank
        String createdBy) {

}
