# 导入 asyncio。
import asyncio

# 用 async def 定义函数 fake_summary()，表示一个可以暂停等待的异步函数
async def fake_summary():
    # 模拟等待 0.2 秒，期间让出执行权
    await asyncio.sleep(0.2)

    # 最后返回字符串 "摘要完成"
    return("摘要完成")

# 定义异步函数 main()
# 在 main() 内，用 result = await fake_summary() 等待摘要，并接住返回值
async def main():

    try:
        # 通过 asyncio.wait_for(...) 等待：
        # - 第一个参数放 fake_summary()，表示要等待的任务
        # - 第二个参数写 timeout=1，表示最多等待约 1 秒
        result = await asyncio.wait_for(fake_summary(),timeout=1)
        print(result)

    # 捕获异常except TimeoutError:
    # 在这个异常分支里打印：等待超时，本次没有拿到摘要
    except TimeoutError:
        print("等待超时，本次没有拿到摘要。")
    

# 启动程序
asyncio.run(main())