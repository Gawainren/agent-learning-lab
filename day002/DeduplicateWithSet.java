package day002;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeduplicateWithSet {
    public static List<Integer> uniqueNumbers(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();

        for (Integer num : numbers) {
            //seen 中还不存在 num。
            if (!seen.contains(num)) {
                //把 num 记入 seen。
                seen.add(num);
                //把首次出现的 num 加入 result。
                result.add(num);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(uniqueNumbers(List.of(3, 1, 3, 2, 1)));
        System.out.println(uniqueNumbers(List.of()));
        System.out.println(uniqueNumbers(List.of(2, 2, 2)));
        System.out.println(uniqueNumbers(List.of(3, 1, 2)));
    }
}