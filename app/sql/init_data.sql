-- password: xcf12fg3
INSERT INTO user(id, username, email, password, roles)
VALUES (1, 'user', 'user@mail.com', '$2a$10$74yMegRgwREZVS72aEKGg.TtRE.KMWE4ly0pvM3l5vn4IN.hR4aYK',
        'ROLE_USER');



INSERT INTO app_type(id, name, comp)
VALUES (1, '英语单词本', 'EnglishWordBook'),
       (2, '力扣题解', 'LeetCodeNote');


-- 用户自定义标签
INSERT INTO tag(name, creator)
VALUES ('数据结构', 1),
       ('算法', 1);