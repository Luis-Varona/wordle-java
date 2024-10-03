import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String[] validWords = WordLists.getValidWords();
        String word = validWords[new Random().nextInt(validWords.length)];
        WordTree wordTree = new WordTree(validWords);
        
        WordleGame game = new WordleGame(word, 6, wordTree);
        game.play();
    }
}