# Agent Learning Lab

记录 Java + Python 企业级 Agent 学习过程，以及每天通过验收的代码和实验结果。

## 技术方向

- Java：业务模型、权限、事务和数据持久化
- Python：文本处理、模型调用、RAG 和 Agent 能力
- 最终目标：完成一个可部署、可评测、可解释的企业级 Agent 项目

## 当前进度

| 学习日 | 内容 | 状态 |
| --- | --- | --- |
| Day001 | 环境准备、列表去重和关键词清洗 | 已完成 |
| Day002 | Java 对象建模、Repository 接口和哈希表 | 已完成 |
| Day003 | List/Map 仓库、复杂度和重复 ID 检查 | 已完成 |
| Day004 | 明确异常语义、文件保存和重启恢复 | 已完成 |
| Day005 | Python 文本清洗与字母异位词练习 | 已完成 |
| Day006–Day007 | JSON 往返转换、必需字段与空白标题校验、复盘 | 已完成 |
| Day008 | Spring Boot HTTP 笔记接口与状态码验证 | 已完成 |
| Day009 | Controller/Service 职责拆分与构造器依赖注入 | 已完成 |
| Day010 | 请求编号校验与统一异常响应 | 已完成，提示后通过 |
| Day011 | MySQL建表、插入与主键约束 | 已完成，提示后通过 |
| Day012 | MyBatis参数绑定、按用户筛选与稳定分页 | 2026-09-13完成，提示后通过；用时未记录 |

## 目录

- `day001/`：Java/Python 基础练习
- `day002/`：Note 模型、Repository 接口和集合练习
- `day003/`：List/Map 仓库实现与契约测试
- `day004/`：严格仓库异常、TSV 文件保存、坏数据保护与重启恢复
- `day005/`：Python 文本清洗与 Java 字母异位词练习
- `day006/`：Day006–Day007 共用的 JSON 转换与字段校验代码
- `day008/notes-api/notes-api/`：Day008–Day010、Day012 共用的 Spring Boot 笔记接口工程
- `day011/notes.sql`：建表、四条练习样本与Day012分页SQL；按需选中语句执行，已有表和主键数据时不要重复执行整份文件

## 运行当前笔记接口

需要 Java 17、Maven 和可连接的 MySQL，库名为 `agent_learning`，表和样本见 `day011/notes.sql`。从仓库根目录打开 PowerShell，设置当前终端环境并启动（用户名按本机实际配置填写，密码交互输入）：

```powershell
. .\day001\准备终端.ps1
$env:DB_URL = 'jdbc:mysql://localhost:3306/agent_learning'
$env:DB_USERNAME = Read-Host 'MySQL 用户名'
$dbSecret = Read-Host 'MySQL 密码' -AsSecureString
$env:DB_PASSWORD = [System.Net.NetworkCredential]::new('', $dbSecret).Password
Remove-Variable dbSecret
cd day008/notes-api/notes-api
mvn spring-boot:run '-Dspring-boot.run.arguments=--server.port=18092'
```

保持服务运行，在另一个终端执行：

```powershell
curl.exe --max-time 15 -i "http://localhost:18092/api/notes?ownerId=10&page=1"
curl.exe --max-time 15 -i "http://localhost:18092/api/notes?ownerId=10&page=2"
curl.exe --max-time 15 -i "http://localhost:18092/api/notes?ownerId=20&page=1"
curl.exe --max-time 15 -i "http://localhost:18092/api/notes?ownerId=10&page=0"
```

2026-09-13 本人提供的实际响应，助手已核对保存源码：前三条均为HTTP200，笔记id分别为 `[1,3]`、`[4]`、`[2]`；page=0返回HTTP400和 `{"code":"INVALID_ID","message":"页码必须大于0"}`，无堆栈。数据集固定，每页2条，按id升序；未测试并发数据变动。

Controller接收参数，Service校验页码并计算long offset，再调用Mapper；MyBatis绑定ownerId、limit、offset并查询MySQL。按传入用户编号筛选不等同于身份认证或权限控制。

旧 `/api/notes/{id}` 对正编号仍返回固定内存笔记，没有按id查询数据库。页码错误的code仍为INVALID_ID，含义待完善，本次不追加改造。

本轮记录了本人提供的Mapper阶段 `mvn compile` 日志（5个主源文件，BUILD SUCCESS）；之后Service与Controller的修改通过上述实际HTTP响应验收。完整打包、完整测试未验证；历史Day009的Surefire插件问题没有本轮解决证据。

核心代码本人编写，Day012整体为提示后通过。下次短复习优先Controller如何把参数传给Service并返回列表。工程、复习与记录用时均未记录。Git提交与线上打卡情况单独核验，不由本README的完成状态推定。

## 提交规则

只提交源码、测试和必要文档，不提交 `.class`、密钥、虚拟环境、本地数据库和依赖目录。
