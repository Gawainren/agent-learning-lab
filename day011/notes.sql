-- 使用agent_learning库
USE agent_learning;
-- 建表
CREATE TABLE notes(
    id BIGINT PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL
);
-- 查询表结构
DESCRIBE agent_learning.notes;
-- 插入数据
INSERT INTO notes(id,owner_id,title,content)
VALUES(1,10,'VPN 排障','检查网络');
-- 查询
SELECT id,owner_id,title,content
FROM agent_learning.notes
WHERE id = 1;

-- Day011：插入并按 id 查询一致；重复 id=1 被主键拒绝；原记录保持不变。