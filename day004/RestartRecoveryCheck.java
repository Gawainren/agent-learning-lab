package day004;

import java.nio.file.Path;
import java.util.List;
import day002.Note;

public class RestartRecoveryCheck {
    public static void main(String[] args) {
        Path path = Path.of("day004","notes-smoke.tsv");
        NoteFileStore store = new NoteFileStore();
        List<Note> loadedNotes = store.load(path);
        StrictMapNoteRepository strictMapNoteRepository =new StrictMapNoteRepository();
        for (Note note : loadedNotes) {
            strictMapNoteRepository.save(note);
        }
        Note first = strictMapNoteRepository.findById(101);
        Note second = strictMapNoteRepository.findById(102);
        if (first.getId()!=101L) {
            throw new AssertionError("失败");
        }
        if (first.getOwnerId()!=7L) {
            throw new AssertionError("失败");
        }
        if (!("VPN 手册".equals(first.getTitle()))) {
            throw new AssertionError("失败");
        }
        if (second.getId()!=102L) {
            throw new AssertionError("失败");
        }
        if (second.getOwnerId()!=8L) {
            throw new AssertionError("失败");
        }
        if (!("邮箱手册".equals(second.getTitle()))) {
            throw new AssertionError("失败");
        }
        System.out.println("RESTART RECOVERY PASS");
    }
}
