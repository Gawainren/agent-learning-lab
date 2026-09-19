package algorithms.a01;

import java.util.HashMap;
import java.util.Map;

public class A01Review {
    public static void main(String[] args) {
        String s = "aab";
        String t = "aba";
        Map<Character,Integer> note = new HashMap<>();
        if (s.length() != t.length()) {
            System.out.println(false);
            return ;
        }
        for(char ch : s.toCharArray()){
            int sum = 0;
            sum = note.getOrDefault(ch, 0);
            note.put(ch, sum+1);
        }
        for(char ch : t.toCharArray()){
            int num = note.getOrDefault(ch, 0);
            if (num == 0) {
                System.out.println(false);
                return;
            }
            note.put(ch, num-1);
        }
        System.out.println(true);
    }
}
