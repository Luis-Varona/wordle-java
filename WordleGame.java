import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class WordleGame {
    //
    private final String word;
    private final int attemptsAllowed;
    private final UniqueLetters uniqueLetters;
    
    public WordleGame(String word, int attemptsAllowed) {
        this.word = word;
        this.attemptsAllowed = attemptsAllowed;
        this.uniqueLetters = getUniqueLetters(word);
    }
    
    //
    private UniqueLetters getUniqueLetters(String word) {
        ArrayList<Character> letters = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();
        ArrayList<TreeSet<Integer>> indices = new ArrayList<>();
        
        for (char c : word.toCharArray()) {
            if (!letters.contains(c)) {
                letters.add(c);
                counts.add(1);
                indices.add(new TreeSet<>());
            } else {
                int index = letters.indexOf(c);
                counts.set(index, counts.get(index) + 1);
                indices.get(index).add(word.indexOf(c));
            }
        }
        
        return new UniqueLetters(letters, counts, indices);
    }
    
    //
    private int[] compareGuess(String guess) {
        int[] comparisons = new int[5];
        Arrays.fill(comparisons, -1);
        UniqueLetters guessUniqueLetters = getUniqueLetters(guess);
        int wordIndex = 0;

        for (char c : uniqueLetters.getLetters()) {
            int guessIndex = guessUniqueLetters.getLetters().indexOf(c);
            
            if (guessIndex != -1) {
                int letterCountWord = uniqueLetters.getCounts().get(wordIndex);
                
                TreeSet<Integer> letterIndicesGuess = guessUniqueLetters
                    .getIndices()
                    .get(guessIndex);
                
                TreeSet<Integer> indicesCorrect = new TreeSet<Integer>(letterIndicesGuess);
                indicesCorrect.retainAll(uniqueLetters.getIndices().get(wordIndex));
                
                for (int i : indicesCorrect) {
                    letterCountWord--;
                    guessUniqueLetters.removeIndex(guessIndex, i);
                    comparisons[i] = 1;
                }
                
                for (int i : letterIndicesGuess) {
                    if (letterCountWord <= 0) break;
                    letterCountWord--;
                    comparisons[i] = 0;
                }
            }
            
            wordIndex++;
        }
        
        return comparisons;
    }
}
