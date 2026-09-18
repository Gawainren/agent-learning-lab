package com.example.notes_api;


import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

@Service 
public class NoteService {
    public Map<String, Object> getNote(long id, long ownerId) {
        // 调用 noteMapper.findByIdAndOwner(id, ownerId)，结果保存到 data
        Map<String,Object> data = noteMapper.findByIdAndOwner(id, ownerId);
        // 如果 data 为 null，抛出下面说明的异常
        if (data == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "笔记不存在");
        }
        // 返回 data
        return data;
    }

    public void updateTitle(long id, long ownerId, String title) {
        if (id <= 0 || title == null || title.isBlank()) {
            throw new IllegalArgumentException("笔记编号或标题不合法");
        }

        // 调用 noteMapper.updateTitleByIdAndOwner(id, ownerId, title)
        // 将返回的行数保存到 int 类型变量 rows
        int rows = noteMapper.updateTitleByIdAndOwner(id,ownerId,title);

        // 如果 rows 为 0，抛出 404，提示“笔记不存在”
        if (rows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"笔记不存在");
        }
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
