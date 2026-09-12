package com.example.notes_api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    // 处理请求的方法
    @GetMapping("/api/notes/1")
    public Map<String, Object> getNote() {
        Map<String,Object> data = noteService.getNote();
        return data;
    }

}