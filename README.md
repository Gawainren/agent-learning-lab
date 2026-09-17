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
| Day013 | 同库两步写入、失败回滚与正常提交 | 2026-09-14行为验收完成，提示后通过；SQL文件已补齐并核对，用时未记录 |
| Day014 | 现有SQL保存与HTTP查询复验、失败响应、应用重启读回 | 2026-09-15完成，提示后通过；用时未记录 |
| Day015 | Python模块入口与环境配置 | 2026-09-16完成，提示后通过 |
| Day016 | FastAPI清洗请求与响应模型 | 2026-09-16完成，提示后通过 |
| Day017 | 真实模型摘要与结构化输出校验 | 2026-09-16完成，提示后通过；Day015–Day017当天合计120分钟，只计一次 |
| Day018 | asyncio本地延迟替身的超时与取消 | 2026-09-17完成，提示后通过；用时未记录 |

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

## Day013 事务实验（2026-09-14）

本人在MySQL执行，助手依据终端输出验收：第一步插入101，第二步重复id=1报1062；报错后101仍在，ROLLBACK后101消失且原id=1不变。正常路径两条INSERT成功，按要求COMMIT后在诊断窗口查询，101、102内容一致；未单贴COMMIT回执，助手未独立重跑数据库实验。

SQLTools和命令行连接混用曾造成未结束事务及锁等待超时，定位连接并回滚后继续。后续同一事务使用同一个连接。`day013/transactions.sql`已保存失败回滚与正常提交的完整步骤，助手按本人要求补齐注释；不要整份重复执行。

101、102已作为实验数据保留，ownerId=10的数据集不再仅含原三条笔记；上方Day012响应是09-13历史证据，不能直接当作当前尾页预期。本次没有实现Spring事务接口或执行完整打包、测试。

算法`day005/A01Review.java`由本人分步重写，真假、长度不同、空串逐步编译运行通过；扣减及复杂度需提示，功能通过待独立复现。本人确认算法累计30分钟；工程及复习用时未记录。Git与线上同步单独核验。

## Day014 接口与SQL复盘（2026-09-15）

本人提供数据库查询结果：id为1、2、3、4、101、102；id=2属于用户20，其余属于用户10。复用已有保存数据，本轮没有新增写入。

- `GET /api/notes?ownerId=10&page=2`：HTTP200，依次返回id=4（安装说明）、101（正常事务第一条），owner_id均10。
- `GET /api/notes?ownerId=10&page=0`：HTTP400，返回`{"code":"INVALID_ID","message":"页码必须大于0"}`，未包含堆栈。
- 本人按要求停止、重新启动应用，再查询第二页返回相同结果，并明确确认“已重启”。证据来自本人响应和确认，助手没有独立重跑；不代表MySQL服务重启验收。

启动故障：新终端mvn不可识别，按上方准备终端步骤恢复；随后数据库环境变量均未设置，启动报`'url' must start with "jdbc"`，在同一终端设置DB_URL、DB_USERNAME、DB_PASSWORD后请求成功。不要把密码保存到源码或记录。

参数与返回字段、limit与page曾混淆；提示后区分List记录数、Map键值数、空列表与返回路径，待跨天独立复述。page=0的错误码语义仍待完善。当前为SQL保存加HTTP查询链路，不是完整HTTP CRUD；完整打包、完整测试未执行。

`day014/verification.sql`由本人保存，提示后补齐ORDER BY id ASC；助手读回确认三列、表名和升序语句正确，修改后未重跑SQL。此前六条查询输出作为行为证据保留；助手未代写核心SQL。今日无算法，A01仍功能通过待独立复现，下次窗口09-19。工程、复习及合计用时未记录；Git待本人提交推送，未同步线上打卡。

## A01 主动加学（2026-09-15，Day014工程收尾后）

本轮全部完成，整体提示后通过；不等同于独立通过。本人重写`day005/A01Review.java`，助手读回核验并运行；后续本人授权助手替换测试输入，核心逻辑未由助手修改。

| 输入s / t | 实际输出 |
| --- | --- |
| aabc / abbc | false |
| aab / aba | true |
| 空串 / 空串 | true |
| a / 空串 | false |

四组均由JDK17 javac编译后java运行，各只输出一行；当前输入恢复aab/aba。没有新增测试套件或力扣提交证据。

计数扣减手推与重写正确；aab/aba最初预测false，提示比较次数后纠正。空串循环0次及长度检查反例（aab/ab会剩余a=1）解释正确。Map最多26键，toCharArray数组随长度增长，当前额外空间O(n)，不是常数空间实现。

时间复杂度应为O(n)；本人首次答对，但后续把具体次数6、2n当作复杂度，最后由助手纠正。保留真实提示记录，09-19短复查复杂度和一个旧边界，不要求整题重写。本次算法用时及全天合计未记录，不重复计入09-14的30分钟。

本轮算法提交待本人提交推送，Day014已有提交469754b不代表这些新改动已上传。线上打卡未同步。

## Day015 模块入口与环境配置（2026-09-16）

最小工程成果提示后通过，核心代码本人编写。day005/clean_text.py的示例放在__main__判断内，助手实际验收直接运行四行输出、仅导入无输出，均退出0；保留缩进、空串、只有换行与重复清洗结果一致。

day015/config.py读取APP_NAME；缺少变量时抛出明确ValueError，正常路径打印服务名。以下从仓库根目录在PowerShell逐条运行，最后一条预期失败：

```powershell
python -X utf8 -B day005/clean_text.py
python -B -c "import sys; sys.path.insert(0, 'day005'); from clean_text import clean_text"
$env:APP_NAME = 'notes-cleaner'
python -X utf8 -B day015/config.py
Remove-Item Env:APP_NAME -ErrorAction SilentlyContinue
python -X utf8 -B day015/config.py
```

助手实际在各脚本目录运行：配置存在时输出'notes-cleaner'、退出0；缺少时报告“ValueError: 缺少必要环境变量 APP_NAME”、退出1且不打印None。config.py是检查脚本，导入也会执行检查和打印；可安静导入的是clean_text模块。示例无真实密钥，空字符串配置未校验。

本轮只完成Day015，未启动FastAPI或进行打包、完整测试。实际用时未记录。今天无算法安排，A01整体提示后通过、独立掌握待09-19复查；09-17短查模块入口与缺配置控制流。Git待本人提交推送，线上打卡未同步。

## 提交规则

只提交源码、测试和必要文档，不提交 `.class`、密钥、虚拟环境、本地数据库和依赖目录。

## Day016 FastAPI清洗接口（2026-09-16）

最小成果提示后通过，核心代码本人编写。请求模型CleanRequest包含必填字符串text，响应模型CleanResponse包含cleaned_text；POST /clean复用day005.clean_text，统一换行并去掉两端换行。空字符串允许，返回空字符串；未实现摘要或AI调用。

从E:\Agent\练习启动（当前已验证环境：Python3.13、FastAPI0.136.3、Pydantic2.13.4、Uvicorn0.49.0）：

```powershell
python -m uvicorn day016.main:app --host 127.0.0.1 --port 18093
```

访问http://127.0.0.1:18093/docs，展开POST /clean，通过Try it out发送请求。

| 请求体 | 本人提供的HTTP证据 |
| --- | --- |
| {"text":"\r\nVPN guide\r\n"} | 200，{"cleaned_text":"VPN guide"} |
| {"text":123} | 422，string_type，错误定位body.text |
| {"text":""} | 200，{"cleaned_text":""} |

助手读取源码；启动日志及HTTP输出由本人提供，助手未独立重跑。未执行打包或完整测试，缺字段、纯空白、超长及额外字段未做HTTP验收。服务停止状态未确认。

最初混淆普通类型提示与运行时校验、类与函数、赋值与调用、响应字段构造；经提示修正，讲解后正确复述函数传参及返回值。09-17与Day015合并短查一到两项，不记跨天独立掌握。

本人确认今天Day015和Day016合计60分钟，只计一次、分项未拆分，更新上方历史未记录状态；后续Git用时未确认。今天无算法，A01保持整体提示后通过、独立掌握待09-19复查。Day016 Git待本人提交推送，线上打卡未同步。

## Day017 真实模型摘要与结构校验（2026-09-16）

核心代码本人编写，整体提示后通过。day017/summary.py读取DEEPSEEK_API_KEY，用DeepSeek的deepseek-flash非思考模式请求JSON摘要，max_tokens=300；SummaryResult要求summary为字符串、keywords为字符串列表，通过model_validate_json校验后才打印成功。

在已设置DEEPSEEK_API_KEY的PowerShell终端，从仓库根目录执行：

```powershell
python day017/summary.py
```

需要openai和pydantic；本人反馈openai SDK版本2.43.0。密钥只设在环境变量，不写入源码。每次执行会发起真实付费请求。

本人提供真实输出：摘要包含连接前检查网络、输入账号验证、失败后检查账号并联系IT，关键词为VPN、网络、账号、验证、IT支持；输出“校验通过”。失败验收临时关闭网络请求，用固定JSON字符串仅提供summary，得到keywords Field required的ValidationError，成功打印未执行。此失败样本是本地替身，不是真实模型坏响应。之后本人恢复真实请求，助手读回核对，未重复付费运行。

助手未独立发起模型请求，未执行单独编译、HTTP接口、打包或完整测试。缺字段以异常终止脚本；未验证网络故障、输出长度和自动语义检查，缺配置仅核对源码。独立掌握待复查。

本人最终确认09-16的Day015、Day016、Day017累计120分钟，仅计一次，分项未拆分；此前60分钟是中途节点。后续Git用时未确认。今天无算法，A01整体提示后通过、独立掌握待09-19复查；09-17合并短查模型校验与异常控制流一到两项。Git待本人提交推送，线上未同步。

## Day018 本地超时与取消实验（2026-09-17）

核心代码本人编写，整体提示后通过。运行：`python day018/timeout_demo.py`。最终保留sleep(0.2)、wait_for timeout=1，try内包含等待和成功打印，except TimeoutError输出超时提示。

本人提供的行为证据：sleep(3)且timeout=1时输出“等待超时，本次没有拿到摘要。”；改为sleep(0.2)后输出“摘要完成”。此前未捕获异常的堆栈含sleep处CancelledError和最终TimeoutError，展示本地等待取消。助手读回源码，未独立重跑、精确计时或执行完整测试。本次没有调用真实模型，不能证明远端模型停止；未验证阻塞或抑制取消的情况，不承诺严格一秒退出。

print/return、await和try范围经提示修正，09-18复查；本人已整理旧延迟注释，助手读回确认，核心逻辑未变。今日减量、无算法，A01待09-19复查。实际用时未记录，Git待本人提交推送，线上未同步。
