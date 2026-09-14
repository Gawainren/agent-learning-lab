-- Day013：事务与一致性实验，实际学习日期2026-09-14，提示后通过。
-- 本文件保存学习时逐条执行的过程，不是可以一键重复运行的初始化脚本。
-- 同一事务必须在同一个连接中完成；本次写入使用MySQL窗口（连接9）。
-- 本次结束后101、102已提交保留，重新实验前必须检查编号，不能直接整份执行。

-- 一、只读检查：本次autocommit=1；显式START TRANSACTION后仍需提交或回滚。
SELECT @@autocommit;

-- 实验开始时预期：原有id=1存在，实验id=101不存在。
SELECT id, owner_id, title
FROM agent_learning.notes
WHERE id IN (1, 101);

-- 确认存储引擎支持事务；本次结果为InnoDB。
SELECT ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'agent_learning'
  AND TABLE_NAME = 'notes';

-- 二、失败路径：两步写入中的第二步故意失败，再手动回滚。
START TRANSACTION;

-- 第一步：插入101，预期1 row affected；此时尚未提交。
INSERT INTO agent_learning.notes(id,owner_id,title,content)
VALUES(101,10,'事务实验','第一步写入');
-- 当前连接能查到自己的未提交修改；查得到不等于已经提交。
SELECT id,owner_id,title,content
FROM agent_learning.notes
WHERE id = 101;

-- 第二步：原id=1已存在，预期ERROR 1062 Duplicate entry。
-- 这是故意制造的错误，出现后手动执行下面的观察和回滚语句。
INSERT INTO agent_learning.notes(id,owner_id,title,content)
VALUES(1,10,'事务实验第二步','故意触发主键冲突');

-- 本次主键冲突后101仍能查到：报错本身没有撤销整个事务。
SELECT id, owner_id, title, content
FROM agent_learning.notes
WHERE id = 101;

-- 撤销当前事务尚未提交的修改，并结束事务、释放其持有的锁。
ROLLBACK;

-- 回滚验收：仅保留原id=1（VPN 排障/检查网络），101消失。
SELECT id, owner_id, title, content
FROM agent_learning.notes
WHERE id IN (1, 101);

-- 三、正常路径：先确认两个实验编号都空闲；本次此处结果为Empty set。
-- 若已有记录，停在这里，不覆盖已有数据，也不要直接继续插入。
SELECT id, owner_id, title
FROM agent_learning.notes
WHERE id IN (101, 102);

-- 在同一个写入连接开启新事务。
START TRANSACTION;
-- 第一步成功，预期1 row affected。
INSERT INTO agent_learning.notes(id,owner_id,title,content)
VALUES(101,10,'正常事务第一条','第一步成功');
-- 第二步也成功，预期1 row affected；两步此时仍未提交。
INSERT INTO agent_learning.notes(id,owner_id,title,content)
VALUES(102,10,'正常事务第二条','第二步成功');
-- 两步都成功后提交，保留本事务的修改并结束事务。
COMMIT;
-- 提交后切换到诊断窗口（本次连接14），验证其他连接可见。
-- 预期返回101、102两条记录，字段与刚才插入的值一致。
SELECT id, owner_id, title, content
FROM agent_learning.notes
WHERE id IN (101, 102)
ORDER BY id;
