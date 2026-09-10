def clean_text(text: str) -> str:
    # 1. 把 \r\n 替换成 \n
    # 2. 把剩余的 \r 替换成 \n
    # 3. 只去掉两端的 \n，并返回结果
    result=text.replace("\r\n","\n")
    result=result.replace("\r","\n")
    result=result.strip("\n")
    return result

sample = "\r\nVPN 使用说明\r\n    print('连接成功')\r\n\r\n"
print(repr(clean_text(sample)))
print("空串：", repr(clean_text("")))
print("只有换行：", repr(clean_text("\r\n\r\n")))

once = clean_text(sample)
twice = clean_text(once)
print("两次结果相同：", once == twice)