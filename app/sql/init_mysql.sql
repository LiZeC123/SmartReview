DROP DATABASE IF EXISTS smart_review;
CREATE DATABASE smart_review DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_review;

-- 用户基本信息表
CREATE TABLE user
(
    id       INTEGER PRIMARY KEY AUTO_INCREMENT,
    username CHAR(64) NOT NULL,
    email    CHAR(64) NOT NULL UNIQUE,
    password CHAR(64) NOT NULL,
    roles    TEXT, -- 用户角色, 多个角色使用逗号隔开
    enable   TINYINT  NOT NULL DEFAULT 1
);

-- password: xcf12fg3
INSERT INTO user(id, username, email, password, roles)
VALUES (1, 'user', 'user@mail.com', '$2a$10$74yMegRgwREZVS72aEKGg.TtRE.KMWE4ly0pvM3l5vn4IN.hR4aYK',
        'ROLE_USER');


-- 知识信息表
CREATE TABLE knowledge
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    app_type   TINYINT  NOT NULL,
    title      CHAR(64) NOT NULL,
    content    TEXT     NOT NULL,
    difficulty TINYINT  NOT NULL,
    creator    INT      NOT NULL
);
CREATE INDEX creator_index ON knowledge (creator);

-- 应用类型表
CREATE TABLE app_type
(
    id   INTEGER PRIMARY KEY AUTO_INCREMENT,
    name CHAR(16) NOT NULL,
    comp CHAR(32) NOT NULL
);


INSERT INTO app_type(id, name, comp)
VALUES (1, '英语单词本', 'EnglishWordBook'),
       (2, '力扣题解', 'LeetCodeNote');


-- 标签基本信息表
CREATE TABLE tag
(
    id      INTEGER PRIMARY KEY AUTO_INCREMENT,
    name    CHAR(32),
    creator INT NOT NULL
);

CREATE UNIQUE INDEX unique_tag_name ON tag (creator, name);

-- 用户自定义标签
INSERT INTO tag(name, creator)
VALUES ('数据结构', 1),
       ('算法', 1);

-- 知识点与标签关联表
CREATE TABLE knowledge_tag
(
    knowledge_id INT NOT NULL,
    tag_id       INT NOT NULL,
    PRIMARY KEY (knowledge_id, tag_id)
);
CREATE INDEX idx_tag ON knowledge_tag (tag_id, knowledge_id);


-- 扩展链接表
CREATE TABLE link
(
    id           INTEGER PRIMARY KEY AUTO_INCREMENT,
    knowledge_id INT NOT NULL,
    name         TEXT,
    url          TEXT
);
CREATE INDEX idx_link ON link (knowledge_id);


-- 单词本语料基本信息表
CREATE TABLE sentence
(
    id      INTEGER PRIMARY KEY AUTO_INCREMENT,
    content TEXT
);

-- 知识点与语料关联表
CREATE TABLE knowledge_sentence
(
    knowledge_id INT NOT NULL,
    sentence_id  INT NOT NULL,
    PRIMARY KEY (knowledge_id, sentence_id)
);


-- 知识点复习情况状态表
CREATE TABLE review_state
(
    knowledge_id     INT PRIMARY KEY,
    review_count     SMALLINT NOT NULL,
    memory_level     TINYINT  NOT NULL, -- 当前的记忆等级
    interval_time    SMALLINT NOT NULL, -- 下次复习的间隔时间(小时)
    next_review_time DATETIME NOT NULL  -- 下次复习的具体时刻
);


-- 知识点复习情况详细记录表
CREATE TABLE review_detail
(
    id            INTEGER PRIMARY KEY AUTO_INCREMENT,
    knowledge_id  INT      NOT NULL,
    review_count  SMALLINT NOT NULL,
    last_level    TINYINT  NOT NULL, -- 上次复习时的记忆等级
    current_level TINYINT  NOT NULL, -- 本次复习时的记忆等级
    difficulty    TINYINT  NOT NULL,
    interval_time SMALLINT NOT NULL, -- 预定的复习间隔时间
    elapsed_time  SMALLINT NOT NULL  -- 本次复习时间距离预定复习时间的差值
);
