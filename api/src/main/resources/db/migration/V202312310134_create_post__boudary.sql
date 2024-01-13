CREATE TABLE posts (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   version BIGINT NOT NULL DEFAULT 0,
   likes_count BIGINT NOT NULL DEFAULT 0,
   comments_count BIGINT NOT NULL DEFAULT 0,
   report_count BIGINT NOT NULL DEFAULT 0,
   title VARCHAR(255) NOT NULL,
   content TEXT NOT NULL,
   post_author_id BIGINT NOT NULL,
   created_date DATETIME,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   INDEX (post_author_id)
) ENGINE=InnoDB;

CREATE TABLE post_images (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   url VARCHAR(255) NOT NULL,
   post_id BIGINT NOT NULL,
   INDEX (post_id)
) ENGINE=InnoDB;

CREATE TABLE comment_likes (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   comment_id BIGINT,
   likes_clicker_id BIGINT NOT NULL,
   INDEX (comment_id),
   INDEX (likes_clicker_id)
) ENGINE=InnoDB;

CREATE TABLE post_comments (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   version BIGINT,
   parent_comment_id BIGINT,
   report_count BIGINT NOT NULL DEFAULT 0,
   likes_count BIGINT NOT NULL DEFAULT 0,
   author_id BIGINT NOT NULL,
   content VARCHAR(255) NOT NULL,
   post_id BIGINT NOT NULL,
   updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   INDEX (author_id),
   INDEX (post_id),
   INDEX (parent_comment_id)
) ENGINE=InnoDB;

CREATE TABLE post_likes (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   post_id BIGINT NOT NULL,
   likes_clicker_id BIGINT NOT NULL,
   INDEX (post_id),
   INDEX (likes_clicker_id)
) ENGINE=InnoDB;


