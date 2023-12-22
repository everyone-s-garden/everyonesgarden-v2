CREATE TABLE feedbacks (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   content VARCHAR(255),
   member_id BIGINT NOT NULL,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   INDEX idx_member_id (member_id)
) ENGINE=InnoDB;

CREATE TABLE feedback_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(255),
    feedback_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_feedback_id(feedback_id)
)