CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(31) NOT NULL,
    role VARCHAR(31) NOT NULL,
    nickname VARCHAR(31),
    INDEX idx_email (email)
);
