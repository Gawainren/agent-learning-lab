from pydantic import BaseModel
from fastapi import FastAPI
from day005.clean_text import clean_text

app = FastAPI()

# 规定收到的数据：必须有字符串字段 text
class CleanRequest(BaseModel):
    text:str

# 规定返回的数据：有字符串字段 cleaned_text
class CleanResponse(BaseModel):
    cleaned_text:str

@app.post("/clean",response_model=CleanResponse)
def clean(body: CleanRequest):
    result = clean_text(body.text)
    return CleanResponse(cleaned_text=result)