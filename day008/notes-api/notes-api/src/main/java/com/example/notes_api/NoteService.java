package com.example.notes_api;

import java.util.HashMap;
import java.util.List;
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

    private final NoteMapper noteMapper;
    public  NoteService(NoteMapper noteMapper){
        this.noteMapper = noteMapper;
    }

    public List<Map<String,Object>> getNotes(long ownerId,int page){
        if (page <= 0) {
            throw new IllegalArgumentException("页码必须大于0");
        }
        long offset = (page - 1L) * 2;
        return noteMapper.findByOwner(ownerId, 2, offset);
    }
}
