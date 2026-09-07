def clean_keywords(words: list[str]) -> list[str]:
    # 在这里实现
    result = []
    for word in words:
        word = word.strip()
        word = word.lower()
        if word not in result and word !='':
            result.append(word)
    return result

print(clean_keywords([" RAG ", "rag", " Agent", "", "   ", "JAVA", "agent"]))
# 预期：["rag", "agent", "java"]

print(clean_keywords([]))
# 预期：[]

print(clean_keywords([" ", ""]))
# 预期：[]