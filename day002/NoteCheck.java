package day002;

public class NoteCheck {
    public static void main(String[] args) {
        Note note = new Note(1L, 10L, "旧标题");

        note.rename("  新标题  ");
        System.out.println("合法重命名：title=[" + note.getTitle() + "]");

        try {
            note.rename("   ");
            System.out.println("FAIL：空白标题被接受");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS：空白标题被拒绝");
        }

        System.out.println("拒绝后：title=[" + note.getTitle() + "]");

        try {
            new Note(2L, 10L, "");
            System.out.println("FAIL：空标题对象被创建");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS：构造时空标题被拒绝");
        }

        try {
            note.rename(null);
            System.out.println("FAIL：null 标题被接受");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS：null 标题被拒绝");
        }
    }
}