package com.api.notice.application.service;

import com.api.notice.application.command.NoticeCreateCommand;
import com.api.notice.application.command.NoticeUpdateCommand;
import com.api.notice.application.usecase.NoticeUseCase;
import com.api.notice.domain.entity.Notice;
import com.api.notice.domain.entity.NoticeAttachment;
import com.api.notice.infrastructure.file.FileService;
import com.api.notice.infrastructure.persistence.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeCommandService implements NoticeUseCase {

    private final NoticeRepository noticeRepository;
    private final FileService fileService;

    @Transactional
    @Override
    public void create(NoticeCreateCommand command) {
        Notice notice = Notice.create(command);
        notice.validatePeriod();

        handleAttachments(notice, command.attachments());
        noticeRepository.save(notice);
    }

    @Transactional
    @Override
    public void update(NoticeUpdateCommand command) {
        Notice notice = findNoticeById(command.noticeId());

        notice.getAttachments()
                .forEach(attachment -> fileService.deleteFile(attachment.getFilePath()));

        notice.clearAttachments();
        handleAttachments(notice, command.attachments());

        notice.update(command);
    }

    @Transactional
    @Override
    public void delete(final long noticeId) {
        Notice notice = findNoticeById(noticeId);
        noticeRepository.delete(notice);
    }

    private Notice findNoticeById(long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다."));

    }

    private void handleAttachments(Notice notice, List<MultipartFile> files) {
        if (files == null) {
            return;
        }

        for (MultipartFile file : files) {
            String filePath = fileService.upload(file);
            NoticeAttachment attachment = new NoticeAttachment(filePath, file.getOriginalFilename());
            attachment.validateFileExtension();
            notice.addAttachment(attachment);
        }
    }

}