package com.api.notice.application.usecase;

import com.api.notice.application.command.NoticeCreateCommand;
import com.api.notice.application.command.NoticeUpdateCommand;

public interface NoticeUseCase {
    void create(NoticeCreateCommand command);
    void update(NoticeUpdateCommand command);
    void delete(final long notice_id);
}