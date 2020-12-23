DROP DATABASE IF EXISTS smart_review;
CREATE DATABASE smart_review DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_review;


CREATE TABLE user
(
    id            INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    username      CHAR(64),
    password      CHAR(64),
    role          TINYINT UNSIGNED,
    create_time   DATETIME NOT NULL,
    modified_time DATETIME NOT NULL,
    is_delete     TINYINT  NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='用户基本信息表';

INSERT INTO smart_review.user(id, username, password, role, create_time, modified_time, is_delete)
VALUES (1, 'LiZeC', '123', 0, NOW(), NOW(), 0),
       (2, 'GGBoy', '123', 0, NOW(), NOW(), 0),
       (3, '中文用户', '123', 0, NOW(), NOW(), 0);


CREATE TABLE knowledge
(
    id            INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title         CHAR(128) NOT NULL,
    content       TEXT      NOT NULL,
    create_time   DATETIME  NOT NULL,
    modified_time DATETIME  NOT NULL,
    is_delete     TINYINT   NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='知识信息表';

INSERT INTO smart_review.knowledge(id, title, content, create_time, modified_time, is_delete)
VALUES (1, '溢出', '栈满称为上溢出, 栈空称为下溢出', NOW(), NOW(), 0);

CREATE TABLE tag
(
    tag_id        INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    tag_name      CHAR(64),
    tag_parent    INT UNSIGNED,
    create_time   DATETIME NOT NULL,
    modified_time DATETIME NOT NULL,
    is_delete     TINYINT  NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='标签基本信息表';

INSERT INTO smart_review.tag(tag_id, tag_name, tag_parent, create_time, modified_time, is_delete)
VALUES (1, '数据结构', NULL, NOW(), NOW(), 0),
       (2, '栈的性质', 1, NOW(), NOW(), 0);


CREATE TABLE link
(
    link_id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    link_content  VARCHAR(255),
    create_time   DATETIME NOT NULL,
    modified_time DATETIME NOT NULL,
    is_delete     TINYINT  NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='链接基本信息表';

INSERT INTO smart_review.link(link_id, link_content, create_time, modified_time, is_delete)
VALUES (1, 'https://lizec.top', NOW(), NOW(), 0);

CREATE TABLE knowledge_tag
(
    knowledge_id INT UNSIGNED,
    tag_id       INT UNSIGNED,
    is_delete    TINYINT,
    PRIMARY KEY (knowledge_id, tag_id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='知识点与标签关联表';

INSERT INTO smart_review.knowledge_tag(knowledge_id, tag_id, is_delete)
VALUES (1, 2, 0);

CREATE TABLE knowledge_link
(
    knowledge_id INT UNSIGNED,
    link_id      INT UNSIGNED,
    is_delete    TINYINT,
    PRIMARY KEY (knowledge_id, link_id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1 COMMENT ='知识点与链接关联表';

INSERT INTO smart_review.knowledge_link(knowledge_id, link_id, is_delete)
VALUES (1, 1, 0);


# 简单测试
EXPLAIN
select k.id, k.title, k.content, t.tag_name, l.link_content
from knowledge k
         join knowledge_tag kt on k.id = kt.knowledge_id
         join tag t on t.tag_id = kt.tag_id
         join knowledge_link kl on k.id = kl.knowledge_id
         join link l on l.link_id = kl.link_id
where k.id = 1;