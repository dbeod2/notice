package com.api.notice.adapter.controller;

import com.api.notice.adapter.payload.NoticeCreateRequest;
import com.api.notice.adapter.payload.NoticeDetailResponse;
import com.api.notice.adapter.payload.NoticeUpdateRequest;
import com.api.notice.application.command.NoticeCreateCommand;
import com.api.notice.application.command.NoticeUpdateCommand;
import com.api.notice.application.usecase.NoticeQueryUseCase;
import com.api.notice.application.usecase.NoticeUseCase;
import com.api.notice.domain.dto.NoticeDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeQueryUseCase noticeQueryUseCase;
    private final NoticeUseCase noticeCommandUseCase;

    @GetMapping("/{notice_id}")
    public NoticeDetailResponse get(@PathVariable long notice_id) {
        NoticeDetailDto result = noticeQueryUseCase.getDetail(notice_id);
        return NoticeDetailResponse.from(result);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@ModelAttribute NoticeCreateRequest request) {
        NoticeCreateCommand command = NoticeCreateCommand.builder()
                .title(request.title())
                .content(request.content())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .createdBy(request.createdBy())
                .attachments(request.attachments())
                .build();
        noticeCommandUseCase.create(command);
    }

    @PutMapping(value = "/{notice_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long notice_id,
                       @ModelAttribute NoticeUpdateRequest request) {
        NoticeUpdateCommand command = NoticeUpdateCommand.builder()
                .noticeId(notice_id)
                .title(request.title())
                .content(request.content())
                .attachments(request.attachments())
                .build();
        noticeCommandUseCase.update(command);
    }

    @DeleteMapping("/{notice_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long notice_id) {
        noticeCommandUseCase.delete(notice_id);
    }
}
