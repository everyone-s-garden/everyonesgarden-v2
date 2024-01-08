CREATE TABLE crop_bookmarks (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  crop_post_id BIGINT NOT NULL,
  bookmark_owner_id BIGINT,
  INDEX (crop_post_id),
  INDEX (bookmark_owner_id)
) ENGINE=InnoDB;

CREATE TABLE crop_images (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  crop_posts__id BIGINT,
  image_url VARCHAR(255) NOT NULL,
  INDEX (crop_posts__id)
) ENGINE=InnoDB;

CREATE TABLE crop_posts (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  content TEXT NOT NULL,
  title VARCHAR(255) NOT NULL,
  crop_category VARCHAR(255) NOT NULL, -- Assuming CropCategory is an enum stored as string
  price INT NOT NULL,
  price_proposal BOOLEAN NOT NULL,
  trade_type VARCHAR(255) NOT NULL, -- Assuming TradeType is an enum stored as string
  crop_post_author_id BIGINT NOT NULL,
  version BIGINT NOT NULL,
  trade_status VARCHAR(255) NOT NULL, -- Assuming TradeStatus is an enum stored as string
  bookMarkCount BIGINT NOT NULL,
  buyer_id BIGINT, -- Nullable
  created_date DATE NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX (crop_post_author_id),
  INDEX (buyer_id) -- Assuming buyer_id references another table
) ENGINE=InnoDB;
