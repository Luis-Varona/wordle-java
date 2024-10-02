import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class WordleGame {
    //
    private final String word;
    private final int attemptsAllowed;
    private final WordTree wordTree;
    private final UniqueLetters uniqueLetters;
    
    public WordleGame(String word, int attemptsAllowed, WordTree wordTree) {
        this.word = word;
        this.attemptsAllowed = attemptsAllowed;
        this.wordTree = wordTree;
        this.uniqueLetters = getUniqueLetters(word);
    }
    
    //
    private UniqueLetters getUniqueLetters(String word) {
        ArrayList<Character> letters = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();
        ArrayList<TreeSet<Integer>> indices = new ArrayList<>();
        int numUnique = 0;
        int indexLetter = 0;
        
        for (char c : word.toCharArray()) {
            if (!letters.contains(c)) {
                letters.add(c);
                counts.add(1);
                indices.add(new TreeSet<>());
                indices.get(numUnique).add(indexLetter);
                numUnique++;
            } else {
                int index = letters.indexOf(c);
                counts.set(index, counts.get(index) + 1);
                indices.get(index).add(indexLetter);
            }
            
            indexLetter++;
        }
        
        return new UniqueLetters(letters, counts, indices);
    }
    
    //
    private int[] compareGuess(String guess) {
        int[] comparison = new int[5];
        Arrays.fill(comparison, -1);
        UniqueLetters guessUniqueLetters = getUniqueLetters(guess);
        int wordIndex = 0;
        
        for (char c : uniqueLetters.getLetters()) {
            int guessIndex = guessUniqueLetters.getLetters().indexOf(c);
            
            if (guessIndex != -1) {
                int letterCountWord = uniqueLetters.getCounts().get(wordIndex);
                
                TreeSet<Integer> letterIndicesGuess = guessUniqueLetters
                    .getIndices()
                    .get(guessIndex);
                
                TreeSet<Integer> indicesCorrect = new TreeSet<>(letterIndicesGuess);
                indicesCorrect.retainAll(uniqueLetters.getIndices().get(wordIndex));
                
                for (int i : indicesCorrect) {
                    letterCountWord--;
                    guessUniqueLetters.removeIndex(guessIndex, i);
                    comparison[i] = 1;
                }
                
                for (int i : letterIndicesGuess) {
                    if (letterCountWord <= 0) {
                        break;
                    }
                    
                    letterCountWord--;
                    comparison[i] = 0;
                }
            }
            
            wordIndex++;
        }
        
        return comparison;
    }
    
    //
    public void play() {
        try (Scanner scan = new Scanner(System.in)) {
            int[] goal = new int[5];
            Arrays.fill(goal, 1);
            int[] comparison = new int[5];
            Arrays.fill(comparison, -1);
            int numAttempts = 0;
            
            while (numAttempts < attemptsAllowed && !Arrays.equals(comparison, goal)) {
                String guess = scan.nextLine();
                
                if (!wordTree.isValidWord(guess)) {
                    System.out.println("Please enter a valid 5-letter word.");
                    continue;
                }
                
                comparison = compareGuess(guess);
                System.out.println(Arrays.toString(comparison));
                numAttempts++;
            }
            
            if (Arrays.equals(comparison, goal)) {
                System.out.println(
                        "\nCongratulations! You guessed the word in " +
                        numAttempts +
                        " tries."
                );
            }
            else {
                System.out.println("\nSorry--you've run out of attempts. Better luck next time!");
            }
        }
    }
}
