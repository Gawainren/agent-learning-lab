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

## 目录

- `day001/`：Java/Python 基础练习
- `day002/`：Note 模型、Repository 接口和集合练习
- `day003/`：List/Map 仓库实现与契约测试
- `day004/`：严格仓库异常、TSV 文件保存、坏数据保护与重启恢复
- `day005/`：Python 文本清洗与 Java 字母异位词练习
- `day006/`：Day006–Day007 共用的 JSON 转换与字段校验代码
- `day008/notes-api/notes-api/`：Day008–Day009 共用的 Spring Boot 笔记接口工程

## 运行当前笔记接口

需要 Java 17 和 Maven。在仓库根目录打开 PowerShell，执行：

```powershell
cd day008/notes-api/notes-api
mvn spring-boot:run
```

保持服务运行，在另一个终端执行：

```powershell
curl.exe -i http://localhost:8080/api/notes/1
```

预期返回 HTTP 200、`Content-Type: application/json`，响应体为：

```json
{"id":1,"title":"VPN 排障","content":"检查网络"}
```

Controller 接收请求，通过构造器注入的 NoteService 获取笔记 Map，再由 Spring 转为 JSON。当前只支持固定路径 `/api/notes/1`；访问 `/api/notes/2` 返回 404，因为没有对应的路径映射，尚未实现动态编号查询和数据库存储。

Day009 最新代码已通过编译、服务启动和 HTTP 响应验收。完整打包曾因本地 Maven Surefire 3.5.6 插件依赖缺失失败，仍待处理；不将接口验收等同于完整打包或自动化测试通过。

## 提交规则

只提交源码、测试和必要文档，不提交 `.class`、密钥、虚拟环境、本地数据库和依赖目录。
