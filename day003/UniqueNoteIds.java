package day003;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//相同 id 只保留第一次出现
//保持原始出现顺序
public final class UniqueNoteIds {
    public static List<Long> deduplicate(long[] ids) {
        List<Long> result = new ArrayList<>();
        HashSet<Long> seen = new HashSet<>();
        for(int i=0;i<ids.length;i++){
            if (!seen.contains(ids[i])) {
                result.add(ids[i]);
                seen.add(ids[i]);
            }
        }
        return result;
    }
}