package day002;

public final class Note {
    private final long id;
    private final long ownerId;
    private String title;

    public Note(long id,long ownerId,String title){
        this.id = id;
        this.ownerId = ownerId;
        rename(title);
    }

    public long getId(){
        return id;
    }

    public long getOwnerId(){
        return ownerId;
    }

    public String getTitle(){
        return title;
    }

    public void rename(String newTitle){
        if (newTitle == null || newTitle.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }

        this.title = newTitle.strip();
    }
}
