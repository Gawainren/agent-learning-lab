# 导入 os，再用 from openai import OpenAI 导入客户端。
import os
import json
from pathlib import Path
from openai import OpenAI
from pydantic import BaseModel

# 环境变量优先；未设置时读取练习目录的本地配置，与启动目录无关。
config_path = Path(__file__).resolve().parents[1] / "local-settings.json"
local_config = json.loads(config_path.read_text(encoding="utf-8-sig")) if config_path.exists() else {}
api_key = os.getenv("DEEPSEEK_API_KEY") or local_config.get("DEEPSEEK_API_KEY")

# 如果密钥缺失或为空，抛出 ValueError("缺少 DEEPSEEK_API_KEY")。
if not api_key:
   raise ValueError("缺少 DEEPSEEK_API_KEY")

# 创建 client = OpenAI(...)，传入两个参数：- api_key=api_key：使用你读到的密钥变量。
# - base_url="https://api.deepseek.com"：指定 DeepSeek 服务地址。
client = OpenAI(api_key=api_key,base_url="https://api.deepseek.com")

# 最后一行只打印 "客户端配置完成"，不要打印密钥。
print("客户端配置完成")

# 定义合法输出的结构
class SummaryResult(BaseModel):
   summary : str #摘要文本
   keywords : list[str] #字符串组成的关键词列表

# 准备模型输入
messages = [
   {"role":"system","content":"请概括用户提供的文本。只返回 JSON，包含 summary（字符串）和 keywords（字符串列表）。如果原文包含权限或次数限制，保留权限要求，以及次数限制的周期、上限等含义；原文没有的信息，请不要擅自添加。"},
   {"role":"user","content":"旧版说明曾写所有员工都能导出客户名单，该说明现已作废。现行规则是：仅管理员可以导出，每周最多2次；普通员工只能查看，不能导出。管理员导出前仍需完成审批，紧急情况也不能跳过审批。"}
]

# 模型请求
response = client.chat.completions.create(
   model="deepseek-flash",
   messages = messages,
   response_format={"type":"json_object"},
   extra_body = {"thinking": {"type": "disabled"}},
   max_tokens = 300
   )

# 取出回复
raw_content = response.choices[0].message.content
result = SummaryResult.model_validate_json(raw_content)
print("校验通过，",result)
