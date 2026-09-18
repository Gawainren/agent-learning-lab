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
   {"role":"system","content":"你是笔记摘要助手。只返回JSON对象，包含summary和keywords。summary是简短中文字符串，keywords是字符串列表。不要添加原文没有的信息。"},
   {"role":"user","content":"连接公司VPN前，请先确认网络正常。打开VPN客户端，输入公司账号并完成验证。如果连接失败，先检查账号状态，再联系IT支持。"}
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
