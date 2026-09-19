from pydantic import BaseModel

class SummaryResult(BaseModel):
    summary : str
    keywords : list[str]

raw_content ='{"summary":"连接VPN前先检查网络。","keywords":["VPN","网络"]}'
result = SummaryResult.model_validate_json(raw_content)
print(result)