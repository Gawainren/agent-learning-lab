import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KeywordCleaner {
    public static List<String> cleanKeywords(List<String> words){
        List<String> result = new ArrayList<>();
        for(String word:words){
            word=word.strip();
            word=word.toLowerCase(Locale.ROOT);
            if (!word.isEmpty() && !result.contains(word)) {
                result.add(word);
            }
        }

        return  result;
    }

    public static void main(String[] args) {
        System.out.println(cleanKeywords(List.of(" RAG ", "rag", " Agent", "", "   ", "JAVA", "agent"))); // 预期：[rag, agent, java]
        System.out.println(cleanKeywords(List.of()));         // 预期：[]
        System.out.println(cleanKeywords(List.of(" ", "")));  // 预期：[]
    }
}
