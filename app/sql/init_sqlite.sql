-- 用户基本信息表
CREATE TABLE user
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
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
-- 不同的AppType返回的数据也不再是一致的了，是用同一个查询结构带上类型参数还是使用不同的接口呢？
-- 需要进行update时的查询返回和列表查询的返回也不一致
CREATE TABLE knowledge
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    app_type   TINYINT  NOT NULL,
    title      CHAR(64) NOT NULL,
    content    TEXT     NOT NULL,
    link       TEXT     NOT NULL,
    difficulty INT      NOT NULL,
    creator    INT      NOT NULL
);
CREATE INDEX creator_index ON knowledge (creator);

-- 应用类型表
DROP TABLE  app_type;
CREATE TABLE app_type
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name CHAR(16) NOT NULL,
    comp CHAR(32) NOT NULL
);


INSERT INTO app_type(id, name, comp)
VALUES (1, '英语单词本', 'EnglishWordBook'),
       (2, '力扣题解', 'LeetCodeNote');


-- 标签基本信息表
CREATE TABLE tag
(
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
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

-- 单词本语料基本信息表
CREATE TABLE sentence
(
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
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
CREATE TABLE knowledge_review_state
(
    knowledge_id     INT PRIMARY KEY,
    review_count     TINYINT  NOT NULL DEFAULT 0,
    current_level    TINYINT  NOT NULL,
    current_interval SMALLINT NOT NULL, -- 当前的复习间隔时间(小时)
    next_review_time DATETIME NOT NULL  -- 下次复习的具体时间
);


-- 知识点复习情况详细记录表
CREATE TABLE knowledge_review_detail
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    knowledge_id  INT      NOT NULL,
    last_level    TINYINT  NOT NULL, -- 上次复习时的记忆等级
    current_level TINYINT  NOT NULL, -- 本次复习时的记忆等级
    create_time   DATETIME NOT NULL
);


-- 简单复习模式的倍率数据表
CREATE TABLE simple_review_rate
(
    id    INT PRIMARY KEY,
    param CHAR(80) NOT NULL DEFAULT '0.5|1.0|2.4|4.5|0.4|0.7|2.2|4.0|0.3|0.6|2.0|3.5|0.2|0.5|1.5|3.0'
);

INSERT INTO simple_review_rate(id)
VALUES (1);