package day004;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import day002.Note;
import java.nio.file.StandardCopyOption;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class NoteFileStore {
    public void save(Path path,List<Note> notes){
        if (path==null) {
            throw new IllegalArgumentException("path is required");
        }
        if (notes == null) {
            throw new IllegalArgumentException("notes are required");
        }
        for(Note note:notes){
            if (note==null) {
                throw new IllegalArgumentException("note is required");
            }
        }
        Path tempPath = path.resolveSibling(path.getFileName().toString()+".tmp");
        try (BufferedWriter writer =
                Files.newBufferedWriter(tempPath, StandardCharsets.UTF_8)) {
            for(Note note:notes){
                writer.write(Long.toString(note.getId()));
                writer.write("\t");
                writer.write(Long.toString(note.getOwnerId()));
                writer.write("\t");
                writer.write(note.getTitle());
                writer.newLine();
            }
        } catch (IOException e) {
            cleanupTemp(tempPath, e);
            throw new NoteStorageException(
                    "failed to save notes: " + path,
                    e);
        }

        try {
            Files.move(
                tempPath,
                path,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            cleanupTemp(tempPath, e);
            throw new NoteStorageException(
                "failed to save notes: " + path,e 
            );
        }
    }

    public List<Note> load(Path path){
        if (path == null) {
            throw new IllegalArgumentException("path is required");
        }
        List<Note> loaded = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            String[] parts;
            int linenumber = 0;
            while ((line = reader.readLine()) != null) {
                linenumber+=1;
                parts = line.split("\t", 3);
                if (parts.length!=3) {
                    IllegalArgumentException cause =new IllegalArgumentException("expected 3 fields");
                    throw new NoteStorageException(
                            "invalid note data at line " + linenumber + ": " + path,
                            cause);
                }
                    try {
                        //使用 Long.parseLong(...) 把前两个字段转换为 long。
                        long id = Long.parseLong(parts[0]);
                        long ownerId = Long.parseLong(parts[1]);
                        Note note1 = new Note(id, ownerId, parts[2]);
                        // 把创建的 Note 加入 loaded。
                        loaded.add(note1);
                    } catch (IllegalArgumentException e) {
                        throw new NoteStorageException(
                            "invalid note data at line " + linenumber + ": " + path,
            e);
                    }
                
            }
            // 循环结束后返回 loaded。
            return loaded;
        } catch (IOException e) {
            throw new NoteStorageException("failed to load notes: "+path,e);
        }
    }

    private void cleanupTemp(Path tempPath,IOException originalFailure){
        try{
            Files.deleteIfExists(tempPath);
        }catch(IOException cleanupFailure){
            originalFailure.addSuppressed(cleanupFailure);

        }
        
        
    }
}
