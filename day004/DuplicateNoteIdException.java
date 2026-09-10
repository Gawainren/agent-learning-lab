package day004;

public final class DuplicateNoteIdException extends IllegalArgumentException{
    public DuplicateNoteIdException(long id) {
        super("duplicate note id: "+id);
    }
}
