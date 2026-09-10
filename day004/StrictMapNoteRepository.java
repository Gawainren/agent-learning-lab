package day004;

import java.util.HashMap;
import java.util.Map;
import day002.NoteRepository;
import day002.Note;

public final class StrictMapNoteRepository implements NoteRepository{
    private final Map<Long, Note> notes = new HashMap<>();

    @Override
    public void save(Note note){
        if (note == null) {
            throw new IllegalArgumentException("note is required");
        }

        long id = note.getId();

        if (notes.containsKey(id)) {
            throw new DuplicateNoteIdException(id);
        }

        notes.put(id, note);
        
    }

    @Override 
    public Note findById(long id) {
        Note note = notes.get(id);
        if (note == null) {
            throw new NoteNotFoundException(id);
        }
        return note;
    }
}
