public class WordTrieTester {
    public static void main(String[] args) {
        WordTrie WordTrie = new WordTrie(WordLists.getValidWords());
        String[] testWords = {"kitty", "PuPPy", "kitt", "KITTIES", "abcde"};
        
        for (String word : testWords) {
            System.out.println(
                word + ": " + (WordTrie.isValidWord(word) ? "valid" : "invalid")
            );
        }
    }
}