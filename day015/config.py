# 导入 Python 标准库 os。
import os
import json
from pathlib import Path
# 环境变量优先，未设置时读取本地配置。
config_path = Path(__file__).resolve().parents[1] / "local-settings.json"
local_config = json.loads(config_path.read_text(encoding="utf-8-sig")) if config_path.exists() else {}
app_name = os.getenv("APP_NAME", local_config.get("APP_NAME"))
# 环境变量检查
if app_name is None:
    raise ValueError("缺少必要环境变量 APP_NAME")
# 打印 repr(app_name)，方便区分字符串、空串和 None。
print(repr(app_name))
