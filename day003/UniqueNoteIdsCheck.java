package day003;

import java.util.List;

public final class UniqueNoteIdsCheck {
    public static void main(String[] args) {
        assertDeduplicated(
                "普通重复",
                new long[]{101L, 102L, 101L, 103L, 102L},
                List.of(101L, 102L, 103L));

        assertDeduplicated(
                "空输入",
                new long[]{},
                List.of());

        assertDeduplicated(
                "全部重复",
                new long[]{7L, 7L, 7L},
                List.of(7L));

        assertDeduplicated(
                "没有重复",
                new long[]{3L, 1L, 2L},
                List.of(3L, 1L, 2L));

        System.out.println("UNIQUE NOTE IDS ALL PASS");
    }

    private static void assertDeduplicated(
            String scenario,
            long[] input,
            List<Long> expected) {
        List<Long> actual = UniqueNoteIds.deduplicate(input);

        if (!expected.equals(actual)) {
            throw new AssertionError(
                    scenario + "失败：expected=" + expected + ", actual=" + actual);
        }

        System.out.println("PASS：" + scenario + "，actual=" + actual);
    }
}
