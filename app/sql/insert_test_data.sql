DELETE FROM  knowledge;
DELETE FROM knowledge_tag;
DELETE FROM knowledge_review_state;

INSERT INTO knowledge(id, app_type, title, content, link, difficulty, creator)
VALUES (1, 1, 'Hello x1', 'Hello Content x1', '[{"name": "Test Title","url": "https://lizec.top"}]', 3,
        1),
       (2, 1, 'Hello x2', 'Hello Content x2', '[{"name": "Test Title","url": "https://lizec.top"}]', 4,
        1);

INSERT INTO knowledge_tag(knowledge_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 1);

INSERT INTO knowledge_review_state(knowledge_id, current_level, current_interval, next_review_time)
VALUES (1, 1, 12, '2020-12-12 12:12:12'),
       (2, 1, 12, '2020-12-12 12:12:12');


