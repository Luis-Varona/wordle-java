import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String[] wordleOptions = WordLists.getWordleOptions();
        String word = wordleOptions[new Random().nextInt(wordleOptions.length)];
        WordTree wordTree = new WordTree(WordLists.getValidWords());
        WordleGame game = new WordleGame(word, wordTree);
        game.play();
    }
}