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

# password: xcf12fg3, odj99Wdx, 9d9xs2x
INSERT INTO smart_review.user(id, username, email, password, roles)
VALUES (1, 'user', 'user@mail.com', '$2a$10$74yMegRgwREZVS72aEKGg.TtRE.KMWE4ly0pvM3l5vn4IN.hR4aYK',
        'ROLE_USER'),
       (2, 'admin', 'admin@mail.com',
        '$2a$10$p5Q8hlQVnuHoVTeOFF4Dmu3.5NHa8ON0n8HMYjQV2/.hJT2nDTzu.',
        'ROLE_ADMIN, ROLE_USER'),
       (3, '中文用户', '123@mail.com', '$2a$10$l8OUbd4vpJ9Q15x1OQTHVu9uoUh0aSLlPBpwcSRgeTD4oyc/hRzwC',
        'ROLE_USER');


CREATE TABLE knowledge
(
    id            INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title         CHAR(128)    NOT NULL,
    content       TEXT         NOT NULL,
    creator       INT UNSIGNED NOT NULL,
    link          TEXT         NOT NULL,
    create_time   DATETIME     NOT NULL DEFAULT NOW(),
    modified_time DATETIME     NOT NULL DEFAULT NOW(),
    is_delete     TINYINT      NOT NULL DEFAULT 0
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='知识信息表';

INSERT INTO smart_review.knowledge(id, title, content, creator, link)
VALUES (1, '溢出', '栈满称为上溢出, 栈空称为下溢出', 2, '');


CREATE TABLE tag
(
    tag_id        INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    tag_name      CHAR(64),
    is_catalog    TINYINT  NOT NULL DEFAULT 0,
    create_time   DATETIME NOT NULL DEFAULT NOW(),
    modified_time DATETIME NOT NULL DEFAULT NOW(),
    is_delete     TINYINT  NOT NULL DEFAULT 0
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='标签基本信息表';

INSERT INTO smart_review.tag(tag_id, tag_name, is_catalog)
VALUES (1, '英语笔记本', 1),
       (2, '计算机英语', 0);


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