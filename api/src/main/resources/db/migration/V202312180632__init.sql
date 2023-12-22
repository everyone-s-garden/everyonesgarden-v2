-- 'reports' 테이블 생성
CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_id BIGINT,
    content VARCHAR(255),
    created_at TIMESTAMP,
    report_type VARCHAR(15),  -- 상속을 위한 구별 컬럼
    garden_report_type VARCHAR(31),  -- GardenReport 클래스의 필드
    garden_id BIGINT  -- GardenReport 클래스의 필드
) ENGINE=InnoDB;

-- 인덱스 추가
CREATE INDEX idx_reporter_id ON reports (reporter_id);
CREATE INDEX idx_garden_id ON reports (garden_id);
CREATE INDEX idx_duplicate_garden_report ON reports (garden_id, reporter_id);