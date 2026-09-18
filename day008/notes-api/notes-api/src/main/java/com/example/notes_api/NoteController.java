package com.example.notes_api;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import java.security.Principal;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    // 处理请求的方法
    @GetMapping("/api/notes/{id}")
    public Map<String, Object> getNote(@PathVariable("id") int id,Principal principal) {
        if (id <= 0) {
            throw new IllegalArgumentException("笔记编号必须大于0");
        }
        long ownerId = Long.parseLong(principal.getName());
        return noteService.getNote(id,ownerId);
    }

    @GetMapping("/api/notes")
    public List<Map<String, Object>> getNotes(
            Principal principal,
            @RequestParam("page") int page) {
        // 用 Long.parseLong(principal.getName()) 得到 long 类型的 ownerId
                long ownerId = Long.parseLong(principal.getName());
        // 调用 noteService.getNotes(ownerId, page)，并返回结果
                return noteService.getNotes(ownerId, page);

    }

    @PutMapping("/api/notes/{id}/title")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTitle(
            @PathVariable("id") long id,
            Principal principal,
            @RequestBody Map<String, String> body) {

        // 从 principal 取得用户名，转换为 long ownerId
                long ownerId = Long.parseLong(principal.getName());
        // 调用 noteService.updateTitle，依次传入 id、ownerId、body.get("title")
                noteService.updateTitle(id, ownerId,body.get("title"));
    }

    @GetMapping("/api/csrf")
    public CsrfToken csrf(CsrfToken token) {
        // 返回参数 token
        return token;
    }
}