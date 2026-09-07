package day002;

public interface NoteRepository {
    void save(Note note);
    Note findById(long id);

}
