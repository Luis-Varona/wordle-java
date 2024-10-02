public class WordleGameTester {
    public static void main(String[] args) {
        String word = "kitty";
        WordTree wordTree = new WordTree(WordLists.getValidWords());
        WordleGame game = new WordleGame(word, 5, wordTree);
        game.play();
    }
}