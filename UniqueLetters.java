import java.util.ArrayList;
import java.util.TreeSet;

public class UniqueLetters {
    //
    private final ArrayList<Character> letters;
    private final ArrayList<Integer> counts;
    private final ArrayList<TreeSet<Integer>> indices;
    
    public UniqueLetters(
        ArrayList<Character> letters,
        ArrayList<Integer> counts,
        ArrayList<TreeSet<Integer>> indices
    ) {
        this.letters = letters;
        this.counts = counts;
        this.indices = indices;
    }
    
    //
    public ArrayList<Character> getLetters() {
        return letters;
    }
    
    public ArrayList<Integer> getCounts() {
        return counts;
    }
    
    public ArrayList<TreeSet<Integer>> getIndices() {
        return indices;
    }
    
    //
    
    public void removeIndex(int i, int index) {
        indices.get(i).remove(index);
    }
}
