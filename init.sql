DROP DATABASE IF EXISTS smart_review;
CREATE DATABASE smart_review DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_review;


CREATE TABLE user
(
    id            INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    username      CHAR(64) NOT NULL,
    email         CHAR(64) NOT NULL UNIQUE,
    password      CHAR(64) NOT NULL,
    roles         TEXT COMMENT '用户角色, 多个角色使用逗号隔开',
    enable        TINYINT  NOT NULL DEFAULT 1,
    create_time   DATETIME NOT NULL DEFAULT NOW(),
    modified_time DATETIME NOT NULL DEFAULT NOW()
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='用户基本信息表';

# password: xcf12fg3
INSERT INTO smart_review.user(id, username, email, password, roles)
VALUES (1, 'user', 'user@mail.com', '$2a$10$74yMegRgwREZVS72aEKGg.TtRE.KMWE4ly0pvM3l5vn4IN.hR4aYK',
        'ROLE_USER');


CREATE TABLE knowledge
(
    id            INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    app_type      CHAR(32)     NOT NULL,
    title         CHAR(64)     NOT NULL,
    content       TEXT         NOT NULL,
    creator       INT UNSIGNED NOT NULL,
    link          TEXT         NOT NULL,
    tag           TEXT         NOT NULL COMMENT '冗余存储用户给定的标签',
    create_time   DATETIME     NOT NULL DEFAULT NOW(),
    modified_time DATETIME     NOT NULL DEFAULT NOW(),
    is_delete     TINYINT      NOT NULL DEFAULT 0
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='知识信息表';


CREATE TABLE tag
(
    id            INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name          CHAR(64),
    creator       INT UNSIGNED NOT NULL,
    create_time   DATETIME     NOT NULL DEFAULT NOW(),
    modified_time DATETIME     NOT NULL DEFAULT NOW(),
    is_delete     TINYINT      NOT NULL DEFAULT 0,
    UNIQUE KEY unique_tag_name (creator, name)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='标签基本信息表';

-- 全局标签 与APP类型对应
INSERT INTO smart_review.tag(name, creator)
VALUES ('英语单词本', 0),
       ('力扣题解', 0);

-- 用户自定义标签
INSERT INTO smart_review.tag(name, creator)
VALUES ('数据结构', 1),
       ('算法', 1);


CREATE TABLE knowledge_tag
(
    knowledge_id INT UNSIGNED NOT NULL,
    tag_id       INT UNSIGNED NOT NULL,
    is_delete    TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (knowledge_id, tag_id),
    KEY idx_tag (tag_id, knowledge_id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='知识点与标签关联表';