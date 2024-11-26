CREATE TABLE award (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 主键，自增
    award_id VARCHAR(50) NOT NULL,       -- 奖项唯一标识
    award_key VARCHAR(50) NOT NULL,      -- 奖项关键值
    award_config TEXT,                   -- 奖项配置信息
    award_desc VARCHAR(255),             -- 奖项描述
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='奖项表';

LOCK TABLES `award` WRITE;

INSERT INTO award (award_id, award_key, award_config, award_desc)
VALUES ('A001', 'KEY001', '{"type": "gift", "value": "50"}', '50元礼品卡');

UNLOCK TABLES;