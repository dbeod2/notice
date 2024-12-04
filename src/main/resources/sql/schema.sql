CREATE TABLE IF NOT EXISTS notice (
                        notice_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        content TEXT NOT NULL,
                        start_dt DATE NOT NULL,
                        end_dt DATE NOT NULL,
                        created_at DATETIME NOT NULL,
                        updated_at DATETIME NULL,
                        view_count INT DEFAULT 0,
                        created_by VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS notice_attachment (
                                   notice_attachment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   notice_id BIGINT NOT NULL,
                                   file_path VARCHAR(255) NOT NULL,
                                   file_name VARCHAR(255) NOT NULL,
                                   created_at DATETIME NOT NULL,
                                   FOREIGN KEY (notice_id) REFERENCES notice(notice_id) ON DELETE CASCADE
);