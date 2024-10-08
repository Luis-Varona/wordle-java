import java.util.Random;

public class PlayWordle {
    public static void main(String[] args) {
        String[] wordleOptions = WordLists.getWordleOptions();
        String word = wordleOptions[new Random().nextInt(wordleOptions.length)];
        WordTrie WordTrie = new WordTrie(WordLists.getValidWords());
        WordleGame game = new WordleGame(word, WordTrie);
        game.play();
    }
}