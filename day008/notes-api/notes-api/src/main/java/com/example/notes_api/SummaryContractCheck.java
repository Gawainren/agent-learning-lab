package com.example.notes_api;

import tools.jackson.databind.json.JsonMapper;

public class SummaryContractCheck {
    public static void main(String[] args) {
        String raw = """
                {"summary":"连接VPN前先检查网络。","keywords":["VPN","网络"]}
                """;
        JsonMapper mapper = JsonMapper.builder().build();
        SummaryResult result = mapper.readValue(raw, SummaryResult.class);
        System.out.println(result.getSummary());
        System.out.println(result.getKeywords());
    }
}
