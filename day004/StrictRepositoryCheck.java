package day004;

import day002.NoteRepository;
import day002.Note;

public class StrictRepositoryCheck {
    public static void main(String[] args){
        runContractChecks("StrictRepositoryCheck", new StrictMapNoteRepository());

    }

    private static void runContractChecks(String repositoryName,NoteRepository repository){

        try {
            repository.findById(999L);
            throw new AssertionError("未找到时没有抛出异常");
         } catch (NoteNotFoundException e) {
            if (!"note not found: 999".equals(e.getMessage())) {
                throw new AssertionError("异常消息不正确：" + e.getMessage());
            }
         }

        Note original = new Note(101, 7, "VPN 手册");
        repository.save(original);
        Note forged = new Note(101L, 99L, "伪造内容");
        try {
            repository.save(forged);
            throw new AssertionError("重复 id 没有抛出异常");
        } catch (DuplicateNoteIdException e) {
            if (!"duplicate note id: 101".equals(e.getMessage())) {
                throw new AssertionError("异常消息不正确：" + e.getMessage());
            }
        }

        Note unchanged = repository.findById(101L);
        if (unchanged.getOwnerId()!=7) {
            throw new AssertionError("重复保存改变了 ownerId");
        }
        
        if (!"VPN 手册".equals(unchanged.getTitle())) {
            throw new AssertionError("重复保存改变了 Title");
        }
        System.out.println("DUPLICATE ID PASS");

        
        try {
            repository.save(null);
            throw new  AssertionError("没有抛出异常");
        } catch (IllegalArgumentException e) {
            if (!"note is required".equals(e.getMessage())) {
                throw new AssertionError("异常消息不正确：" + e.getMessage());
            }
        }
        System.out.println("NULL NOTE PASS");

        System.out.println("MISSING NOTE PASS");
    }
}
