import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class test {

    public static List<String> cleanwords(List<String> words){
        List<String> result = new ArrayList<>();
        for(String word:words){
            word = word.strip().toLowerCase(Locale.ROOT);
            if (!word.isEmpty() && !result.contains(word)) {
                result.add(word);
            }
        }
        return result;
    }

    public static void main(String[] args) {
         System.out.println(cleanwords(List.of(" API ", "api", " RAG ", "", "   ", "Agent", "AGENT")));//预期：["api", "rag", "agent"]
         System.out.println(cleanwords(List.of()));         // 预期：[]
        System.out.println(cleanwords(List.of(" ", "")));  // 预期：[]
    }
}
