package day003;

import day002.Note;
import day002.NoteRepository;

public final class RepositoryCheck {
    public static void main(String[] args) {
        runContractChecks("List", new ListNoteRepository());
        runContractChecks("Map", new MapNoteRepository());
    }

    private static void runContractChecks(
            String repositoryName,
            NoteRepository repository) {

        System.out.println("开始测试：" + repositoryName);


        // 不再创建 ListNoteRepository，因为 repository 已由参数传入。

            // TODO 1：空仓库查询 101，必须得到 null
            if (repository.findById(101L) != null) {
                throw new AssertionError("空仓库不应找到 101");
            }

            Note original = new Note(101L, 7L, "VPN 手册");

            // TODO 2：保存 original
            repository.save(original);

            // TODO 3：查询 101，结果不能是 null
            Note found = repository.findById(101L);

            if (found == null) {
                throw new AssertionError("保存后没有找到 101");
            }

            if (!"VPN 手册".equals(found.getTitle())) {
                throw new AssertionError("标题不正确");
            }

            // TODO 4：查询结果的标题必须是 "VPN 手册"

            // TODO 5：查询不存在的 999，必须得到 null
            if (repository.findById(999L) != null) {
                throw new AssertionError("不应找到 999");
            }
            Note forged = new Note(101L, 99L, "伪造内容");

            // TODO 6：保存 forged，必须捕获 IllegalArgumentException
            try {
                repository.save(forged);
                throw new AssertionError("重复 id 没有被拒绝");
            } catch (IllegalArgumentException e) {
                System.out.println("PASS：重复 id 被拒绝");
            }
            // TODO 7：失败后重新查询 101
            // ownerId 必须仍为 7，title 必须仍为 "VPN 手册"
            Note unchanged = repository.findById(101L);
            if (unchanged == null) {
                throw new AssertionError("重复保存失败后，原笔记丢失");
            }

            if (unchanged.getOwnerId() != 7L) {
                throw new AssertionError("重复保存改变了 ownerId");
            }

            if (!"VPN 手册".equals(unchanged.getTitle())) {
                throw new AssertionError("重复保存改变了标题");
            }
            System.out.println(repositoryName + " ALL PASS");
    }
}