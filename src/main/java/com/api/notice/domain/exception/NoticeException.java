package com.api.notice.domain.exception;

public sealed class NoticeException extends RuntimeException {
    public NoticeException() {

    }

    public NoticeException(String message) {
        super(message);
    }

    public static final class InvalidPeriod extends NoticeException {
        public InvalidPeriod(String message) {
            super(message);
        }
    }
}