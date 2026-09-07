def unique_numbers(numbers: list[int]) -> list[int]:
    # 在这里实现，不要直接写死返回结果
    a = []
    for num in numbers:
      if num not in a:
         a.append(num)
    return a


print(unique_numbers([3, 1, 3, 2, 1]))  # 预期：[3, 1, 2]
print(unique_numbers([]))               # 预期：[]
print(unique_numbers([2, 2, 2]))        # 预期：[2]
print(unique_numbers([3, 1, 2]))        # 预期：[3, 1, 2]