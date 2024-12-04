-- 공지사항 데이터 삽입
INSERT INTO notice (title, content, start_dt, end_dt, created_at, updated_at, view_count, created_by)
VALUES
    ('공지사항 제목 1', '공지사항 내용 1입니다.', '2024-12-01', '2024-12-31', NOW(), NULL, 10, 'admin'),
    ('공지사항 제목 2', '공지사항 내용 2입니다.', '2024-12-05', '2024-12-20', NOW(), NULL, 25, 'manager'),
    ('공지사항 제목 3', '공지사항 내용 3입니다.', '2024-12-10', '2025-01-10', NOW(), NULL, 5, 'user');

-- 첨부파일 데이터 삽입
INSERT INTO notice_attachment (notice_id, file_path, file_name, created_at)
VALUES
    (1, '/uploads/notice1_file1.pdf', 'notice1_file1.pdf', NOW()),
    (1, '/uploads/notice1_file2.jpg', 'notice1_file2.jpg', NOW()),
    (2, '/uploads/notice2_file1.docx', 'notice2_file1.docx', NOW()),
    (3, '/uploads/notice3_file1.png', 'notice3_file1.png', NOW()),
    (3, '/uploads/notice3_file2.zip', 'notice3_file2.zip', NOW());