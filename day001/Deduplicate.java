import java.util.ArrayList;
import java.util.List;

public class Deduplicate {
    public static List<Integer> uniqueNumbers(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();

        // TODO：遍历 numbers，只把尚未出现的数字加入 result
        for(Integer num:numbers){
            if (!result.contains(num)) {
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