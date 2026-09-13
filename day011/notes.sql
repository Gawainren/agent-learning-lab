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

SELECT id, owner_id, title
FROM agent_learning.notes
ORDER BY id ASC;
-- 插入数据
INSERT INTO notes(id,owner_id,title,content)
VALUES(2,20,'账号申请','开通账号'),
      (3,10,'网络检查','检查连接'),
      (4,10,'安装说明','安装客户端');
-- 查询用户 10，按 id 升序，LIMIT 2 OFFSET 0  查询第一页，预计输出id1、3
SELECT id, owner_id, title
FROM agent_learning.notes
WHERE owner_id = 10
ORDER BY id ASC
LIMIT 2 OFFSET 0;
-- 查询用户 10，按 id 升序，LIMIT 2 OFFSET 2  查询第二页，预计输出id4
SELECT id, owner_id, title
FROM agent_learning.notes
WHERE owner_id = 10
ORDER BY id ASC
LIMIT 2 OFFSET 2;