package com.api.notice.domain.entity;

import com.api.notice.domain.exception.NoticeException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "noticeId", nullable = false)
    private Notice notice;

    @Column(name = "file_path", nullable = false, length = 255)
    private String filePath;

    private String fileName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private static final List<String> ALLOWED_EXTENSIONS =
            List.of("jpg", "jpeg", "png", "pdf", "doc", "docx");

    public NoticeAttachment(String filePath, String originalFilename) {
        this.filePath = filePath;
        this.fileName = originalFilename;
        this.createdAt = LocalDateTime.now();
    }

    public void addNotice(Notice notice) {
        this.notice = notice;
    }

    public void validateFileExtension() {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (!extension.isBlank() && !ALLOWED_EXTENSIONS.contains(extension)) {
            throw new NoticeException.InvalidPeriod("지원하지 않는 파일 형식입니다: " + extension);
        }
    }

}