package day004;

public final class NoteNotFoundException extends RuntimeException{
    public NoteNotFoundException(long id){
        super("note not found: "+id);
    }
}