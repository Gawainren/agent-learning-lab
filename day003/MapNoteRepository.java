package day003;

import day002.Note;
import day002.NoteRepository;

import java.util.HashMap;
import java.util.Map;

public final class MapNoteRepository implements NoteRepository {
    private final Map<Long, Note> notes = new HashMap<>();

    @Override
    public void save(Note note) {
        long id = note.getId();//从待保存的 note 取得 id

        if (notes.containsKey(id)) {//判断 Map 是否已有这个键
            throw new IllegalArgumentException("duplicate id");
        }

        notes.put(id, note);//将 id → Note 放入 Map。
    }

    @Override
    public Note findById(long id) {
        return notes.get(id);//根据 id 取得 Note。
    }
}