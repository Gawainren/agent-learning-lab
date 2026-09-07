package day002;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoSumWithMap {
    public static int[] twoSum(List<Integer> numbers, int target) {
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < numbers.size(); i++) {
            int num = numbers.get(i);
            int need = target-num;

            // 先查询 need
            if (seen.containsKey(need)) {
                return new int[]{seen.get(need), i};
            }

            // 没找到，再保存“当前数字 → 当前下标”
            seen.put(num, i);
        }

        throw new IllegalArgumentException("no solution");
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(
                twoSum(List.of(2, 7, 11, 15), 9)));

        System.out.println(Arrays.toString(
                twoSum(List.of(3, 2, 4), 6)));

        System.out.println(Arrays.toString(
                twoSum(List.of(3, 3), 6)));
        try {
            twoSum(List.of(1, 2, 5), 10);
            System.out.println("FAIL：无解时没有抛出异常");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS：无解时抛出异常，message=" + e.getMessage());
        }
    }
}