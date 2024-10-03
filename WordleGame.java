import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class WordleGame {
    private final String word;
    private final WordTree wordTree;
    private final UniqueLetters uniqueLetters;
    
    public WordleGame(String word, WordTree wordTree) {
        this.word = word;
        this.wordTree = wordTree;
        this.uniqueLetters = getUniqueLetters(word);
    }
    
    //
    public void play() {
        System.out.println(
            "Welcome to Wordle! Try to guess the 5-letter word in 6 attempts. " +
            "Enter a valid 5-letter word to begin. Meow! <3"
        );
        
        try (Scanner scan = new Scanner(System.in)) {
            int numAttempts = 0;
            String guess = "00000";
            
            while (numAttempts < 6 && !guess.equals(word)) {
                guess = scan.nextLine().toLowerCase();
                
                if (!wordTree.isValidWord(guess)) {
                    System.out.println(
                        "Invalid input. Please enter a valid 5-letter word."
                    );
                }
                else {
                    System.out.println(new ColoredComparison(guess, compareGuess(guess)));
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
    
    private record ColoredComparison(String word, int[] comparison) {
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
            
            return sb.append(ANSI_RESET).toString();
        }
    }
    
    //
    private int[] compareGuess(String guess) {
        int[] comparison = new int[5];
        Arrays.fill(comparison, -1);
        UniqueLetters guessLetters = getUniqueLetters(guess);
        int indexWord = 0;
        
        for (char c : uniqueLetters.letters()) {
            int indexGuess = guessLetters.letters().indexOf(c);
            
            if (indexGuess != -1) {
                int repsRemaining = uniqueLetters.counts().get(indexWord);
                TreeSet<Integer> indicesGreen = guessLetters.indices().get(indexGuess);
                Iterator<Integer> indicesYellow = new TreeSet<>(indicesGreen).iterator();
                indicesGreen.retainAll(uniqueLetters.indices().get(indexWord));
                
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
    
    private record UniqueLetters(
        ArrayList<Character> letters,
        ArrayList<Integer> counts,
        ArrayList<TreeSet<Integer>> indices
    ) { }
}