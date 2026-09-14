package day005;

import java.util.HashMap;
import java.util.Map;

public class A01Review {
    public static void main(String[] args) {
        String s = "";
        String t = "";
        if (s.length() != t.length()) {
            System.out.println(false);
            return;
        }
        Map<Character,Integer> counts = new HashMap<>();
        for(char ex:s.toCharArray()){
            int num = 0;
            num = counts.getOrDefault(ex, 0);
            counts.put(ex, num+1);
        }
        for(char ex:t.toCharArray()){
            int num = counts.getOrDefault(ex, 0);
            if (num == 0) {
                System.out.println(false);
                return;
            }
            counts.put(ex, num-1);
        }
        System.out.println(true);
    }
}
