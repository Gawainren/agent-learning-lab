package day005;

import java.util.HashMap;
import java.util.Map;

public class A01 {
    public static void main(String[] args) {
        String s = "";
        String t = "";
        Map<Character, Integer> counts = new HashMap<>();

        if (s.length()!=t.length()) {
            System.out.println(false);
            return ;
        }
        for (char ch : s.toCharArray()) {
            int num = 0;
            // 你来补：取出当前次数，加 1，再用 put 存回去
            num = counts.getOrDefault(ch, 0);
            counts.put(ch, num+1);
        }
        System.out.println(counts);

        for (char ch : t.toCharArray()) {
            int remaining = counts.getOrDefault(ch, 0);
            // 1. 如果 remaining 为 0，打印 false，然后 return 结束 main
            if (remaining==0) {
                System.out.println(false);
                return ;
            }
            // 2. 否则，把这个字母的剩余次数减 1，存回 counts
            counts.put(ch, remaining-1);
        }
        System.out.println(true);
    }
}