# 导入 Python 标准库 os。
import os
# 用 os.getenv("APP_NAME") 读取环境变量，保存到 app_name。
app_name = os.getenv("APP_NAME")
# 环境变量检查
if app_name is None:
    raise ValueError("缺少必要环境变量 APP_NAME")
# 打印 repr(app_name)，方便区分字符串、空串和 None。
print(repr(app_name))