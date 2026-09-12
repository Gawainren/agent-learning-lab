package com.example.notes_api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    // 处理请求的方法
    @GetMapping("/api/notes/{id}")
    public Map<String, Object> getNote(@PathVariable("id") int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("笔记编号必须大于0");
        }
        Map<String,Object> data = noteService.getNote();
        return data;
    }

}