public class WordTreeTester {
    public static void main(String[] args) {
        WordTree wordTree = new WordTree(WordLists.getValidWords());
        String[] testWords = {"kitty", "PuPPy", "kitt", "KITTIES", "abcde"};
        
        for (String word : testWords) {
            System.out.println(
                word + ": " + (wordTree.isValidWord(word) ? "valid" : "invalid")
            );
        }
    }
}