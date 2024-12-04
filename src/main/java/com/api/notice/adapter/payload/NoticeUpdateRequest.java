package com.api.notice.adapter.payload;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record NoticeUpdateRequest(
        @NotBlank
        String title,
        @NotBlank
        String content,
        List<MultipartFile> attachments) {

}
