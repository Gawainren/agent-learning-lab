package day003;

import day002.Note;
import day002.NoteRepository;
import java.util.ArrayList;
import java.util.List;

public final class ListNoteRepository implements NoteRepository {
    private final List<Note> notes = new ArrayList<>();
    @Override
    public void save(Note note) {
        for(int i =0;i<notes.size();i++){
            Note current = notes.get(i);//当前位置的Note对象
            if (current.getId() == note.getId()) {//已有笔记id==新笔记id  .getid()获取对应笔记id
                 throw new IllegalArgumentException("duplicate id");
            }
        }
        notes.add(note);
    }
    @Override
    public Note findById(long id) {
        for(int i =0;i<notes.size();i++){
            Note current = notes.get(i);
            if (current.getId() == id) {//当内容的id==查询id
                return current;//输出找到的内容
            }
        }
        return null;
    }
}