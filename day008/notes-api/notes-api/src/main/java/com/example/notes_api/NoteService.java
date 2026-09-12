package com.example.notes_api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service 
public class NoteService {
    public Map<String,Object> getNote(){
        Map<String,Object> data = new HashMap<>();
        data.put("title", "VPN 排障");
        data.put("id", 1);
        data.put("content", "检查网络");
        return data;
    }
}
