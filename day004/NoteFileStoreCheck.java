package day004;

import java.nio.file.Path;
import java.util.List;
import day002.Note;
import java.io.IOException;
import java.util.ArrayList;

public class NoteFileStoreCheck {
    public static void main(String[] args) {
        NoteFileStore noteFileStore = new NoteFileStore();
        Path output= Path.of("day004", "notes-smoke.tsv");
        Note note1 = new Note(101L, 7L, "VPN 手册");
        Note note2 = new Note(102L, 8L, "邮箱手册");
        noteFileStore.save(output, List.of(note1,note2));
        System.out.println("TWO NOTES SAVE PASS");
        
        Path newoutput = Path.of("day004");
        try {
            noteFileStore.save(newoutput, List.of(note1));
            throw new AssertionError("没有异常");
        } catch (NoteStorageException e) {
            if (!"failed to save notes: day004".equals(e.getMessage())) {
                throw new AssertionError("失败");
            }
            if (!(e.getCause() instanceof IOException)) {
                throw new AssertionError("失败");
            }
        }
        System.out.println("SAVE FAILURE PASS");

        try {
            noteFileStore.save(null, List.of(note1));
            throw new AssertionError("没有异常");
        } catch (IllegalArgumentException e) {
            if (!"path is required".equals(e.getMessage())) {
                throw new AssertionError("失败");
            }
        }
        System.out.println("NULL PATH PASS");

        try {
            noteFileStore.save(output, null);
            throw new AssertionError("没有异常");
        } catch (IllegalArgumentException e) {
            if (!"notes are required".equals(e.getMessage())) {
                throw new AssertionError("失败");
            }
        }
        System.out.println("NULL NOTES PASS");

        List<Note> notes1List = new ArrayList<>();
        notes1List.add(note1);
        notes1List.add(null);
        try {
            noteFileStore.save(output, notes1List);
            throw new AssertionError("没有异常");
        } catch (IllegalArgumentException e) {
            if (!"note is required".equals(e.getMessage())) {
                throw new AssertionError("失败");
            }
        }
        System.out.println("NULL ELEMENT PASS");

        Path emptyPath = Path.of("day004", "empty-notes.tsv");
        noteFileStore.save(emptyPath, List.of());        
        List<Note> a1 = noteFileStore.load(emptyPath);
        
        if (!a1.isEmpty()) {
            throw new AssertionError("结果不是空列表");
        }
        System.out.println("EMPTY LOAD PASS");



        
        List<Note> restored = noteFileStore.load(output);
        if (restored.size()!=2) {
            throw new AssertionError("数量异常");
        }
        Note first = restored.get(0);
        Note second = restored.get(1);
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
        System.out.println("TWO NOTES LOAD PASS");

        Path corruptPath = Path.of("day004", "corrupt-notes.tsv");
         
        List<Note> activeNotes = noteFileStore.load(output);
        try {
            activeNotes = noteFileStore.load(corruptPath);
            throw new AssertionError("无异常");
        } catch (NoteStorageException e) {
            String expectedMessage ="invalid note data at line 2: " + corruptPath;
            if (!expectedMessage.equals(e.getMessage())) {
                throw new AssertionError("失败");
            }
            if (!(e.getCause() instanceof IllegalArgumentException)) {
            throw new AssertionError("cause 类型不正确");
        }
            if (!("expected 3 fields".equals(e.getCause().getMessage()))) {
                throw new AssertionError("失败");
            }
        }
        if (activeNotes.size()!=2) {
            throw new AssertionError("数量异常");
        }
        first = activeNotes.get(0);
        second = activeNotes.get(1);
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
        System.out.println("CORRUPT LOAD PRESERVES OLD DATA PASS");
        System.out.println("CORRUPT LINE PASS");
        
        Path output1 = Path.of("day004", "invalid-number-notes.tsv");
        try {
            noteFileStore.load(output1);
            throw new AssertionError("无异常");
        } catch (NoteStorageException e) {
            String expextedMessage =  "invalid note data at line 2: " + output1;
            if (!expextedMessage.equals(e.getMessage())) {
                throw new AssertionError(
                        "异常消息不正确：" + e.getMessage());
            }

            if (!(e.getCause() instanceof NumberFormatException)) {
                throw new AssertionError("cause 类型不正确");
            }
        System.out.println("INVALID NUMBER PASS");
        }
    }
}