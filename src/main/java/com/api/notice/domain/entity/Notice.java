package com.api.notice.domain.entity;

import com.api.notice.application.command.NoticeCreateCommand;
import com.api.notice.application.command.NoticeUpdateCommand;
import com.api.notice.domain.exception.NoticeException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "start_dt", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_dt", nullable = false)
    private LocalDate endDate;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Builder.Default
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeAttachment> attachments = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Notice create(final NoticeCreateCommand command) {
        Notice notice = new Notice();
        notice.title = command.title();
        notice.content = command.content();
        notice.startDate = command.startDate();
        notice.endDate = command.endDate();
        notice.createdBy = command.createdBy();
        notice.createdAt = LocalDateTime.now();
        return notice;
    }

    public void update(NoticeUpdateCommand command) {
        validatePeriod();
        this.title = command.title();
        this.content = command.content();
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseViewCount() {
        this.viewCount = this.viewCount + 1;
    }

    public void addAttachment(NoticeAttachment attachment) {
        if (Objects.isNull(attachment)) {
            return;
        }
        attachment.addNotice(this);
        this.attachments.add(attachment);
    }

    public void clearAttachments() {
        this.attachments.clear();
    }

    public void validatePeriod() {
        if (startDate.isAfter(endDate)) {
            throw new NoticeException.InvalidPeriod("시작일이 종료일보다 늦을 수 없습니다.");
        }
        if (endDate.isBefore(LocalDate.now())) {
            throw new NoticeException.InvalidPeriod("종료일이 현재보다 이전일 수 없습니다.");
        }
    }
}
