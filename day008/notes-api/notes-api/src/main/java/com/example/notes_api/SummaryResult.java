package com.example.notes_api;

import java.util.List;

public class SummaryResult {
    private String summary;
    private List<String> keywords;

    public String getSummary(){
        return summary;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }

    public List<String> getKeywords(){
        return keywords;
    }
    public void setKeywords(List<String> keywords){
        this.keywords = keywords;
    }
}
