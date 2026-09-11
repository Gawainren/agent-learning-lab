package com.example.notes_api;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    // 在这里编写处理请求的方法
    @GetMapping("/api/notes/1")
    public Map<String, Object> getNote() {
        // 这里由你补上创建 Map 并 return 的代码
        Map<String,Object> data = new HashMap<>();
        data.put("title", "VPN 排障");
        data.put("id", 1);
        data.put("content", "检查网络");
        return data;
    }

}