import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class WordleGame {
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
    public void play() {
        System.out.println(
            "Welcome to Wordle! Try to guess the 5-letter word in " +
            attemptsAllowed +
            " attempts. Enter a valid 5-letter word to begin. Meow! <3"
        );
        
        try (Scanner scan = new Scanner(System.in)) {
            int numAttempts = 0;
            String guess = "00000";
            
            while (numAttempts < attemptsAllowed && !guess.equals(word)) {
                guess = scan.nextLine().toLowerCase();
                
                if (!wordTree.isValidWord(guess)) {
                    System.out.println(
                        "Invalid input. Please enter a valid 5-letter word."
                    );
                }
                else {
                    // int[] comparison = compareGuess(guess);
                    // System.out.println(Arrays.toString(comparison));
                    System.out.println(new ColorComparison(guess, compareGuess(guess)));
                    numAttempts++;
                }
            }
            
            if (guess.equals(word)) {
                System.out.println(
                        "\nCongratulations! You guessed the word `" +
                        word +
                        "` in " +
                        numAttempts +
                        " tries. Meow! <3"
                );
            }
            else {
                System.out.println(
                    "\nSorry--you've run out of attempts. The word was `" +
                    word +
                    ".` Better luck next time! Meow! <3"
                );
            }
        }
    }
    
    //
    private UniqueLetters getUniqueLetters(String word) {
        ArrayList<Character> letters = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();
        ArrayList<TreeSet<Integer>> indices = new ArrayList<>();
        int indexLetter = 0;
        
        for (char c : word.toCharArray()) {
            if (!letters.contains(c)) {
                letters.add(c);
                counts.add(1);
                indices.add(new TreeSet<>(Arrays.asList(indexLetter)));
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
        UniqueLetters guessLetters = getUniqueLetters(guess);
        int indexWord = 0;
        
        for (char c : uniqueLetters.getLetters()) {
            int indexGuess = guessLetters.getLetters().indexOf(c);
            
            if (indexGuess != -1) {
                int repsRemaining = uniqueLetters.getCounts().get(indexWord);
                TreeSet<Integer> indicesGreen = guessLetters.getIndices().get(indexGuess);
                Iterator<Integer> indicesYellow = new TreeSet<>(indicesGreen).iterator();
                indicesGreen.retainAll(uniqueLetters.getIndices().get(indexWord));
                
                for (int i : indicesGreen) {
                    comparison[i] = 1;
                    repsRemaining--;
                }
                
                while (indicesYellow.hasNext() && repsRemaining > 0) {
                    int index = indicesYellow.next();
                    
                    if (!indicesGreen.contains(index)) {
                        comparison[index] = 0;
                        repsRemaining--;
                    }
                }
            }
            
            indexWord++;
        }
        
        return comparison;
    }
    
    //
    private class UniqueLetters {
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
        
        public ArrayList<Character> getLetters() {
            return letters;
        }
        
        public ArrayList<Integer> getCounts() {
            return counts;
        }
        
        public ArrayList<TreeSet<Integer>> getIndices() {
            return indices;
        }
    }
    
    //
    private class ColorComparison {
        private final String word;
        private final int[] comparison;
        
        public ColorComparison(String word, int[] comparison) {
            this.word = word.toUpperCase();
            this.comparison = comparison;
        }
        
        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_BLACK = "\u001B[30m";
        private static final String ANSI_GREEN = "\u001B[32m";
        private static final String ANSI_YELLOW = "\u001B[33m";
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            
            for (int i = 0; i < word.length(); i++) {
                switch (comparison[i]) {
                    case 1 -> sb.append(ANSI_GREEN);
                    case 0 -> sb.append(ANSI_YELLOW);
                    default -> sb.append(ANSI_BLACK);
                }
                
                sb.append(word.charAt(i)).append(' ');
            }
            
            sb.append(ANSI_RESET);
            return sb.toString();
        }
    }
}