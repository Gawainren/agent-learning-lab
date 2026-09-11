import json

note = {
    "id": "1",
    "ownerId": "1001",
    "title": "VPN 排障"
}
text = json.dumps(note,ensure_ascii=False)
restored = json.loads(text)
print("text: ",text)
print(type(text))
print(type(restored))
print("往返是否一致:", restored == note)

messing_title_text = '{"id": "2","ownerId": "1001"}'
invalid_note = json.loads(messing_title_text)
print("invalid_note: ",invalid_note)
required_fields = ["id", "ownerId", "title"]
for name in required_fields:
    if name not in invalid_note:
        print("缺少必需字段: ",name)
