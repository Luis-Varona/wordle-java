import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WordTrie {
    private final String[] validWords;
    private final TrieNode trieRoot;
    
    public WordTrie(String[] validWords) {
        this.validWords = validWords;
        this.trieRoot = buildTrie();
    }
    
    public String[] getValidWords() {
        return validWords;
    }
    
    public boolean isValidWord(String word) {
        TrieNode leaf = null;
        
        if (word.length() == validWords[0].length()) {
            leaf = trieRoot;
            
            for (char c : word.toCharArray()) {
                leaf = leaf.findChild(c);
                
                if (leaf == null) {
                    break;
                }
            }
        }
        
        return leaf != null;
    }
    
    //
    private TrieNode buildTrie() {
        Queue<TrieNode> nodeQueue = new LinkedList<>();
        TrieNode root = new TrieNode('\0', "");
        nodeQueue.add(root);
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        
        while (!nodeQueue.isEmpty()) {
            TrieNode node = nodeQueue.remove();
            String substring = node.getSubstring();
            ArrayList<Character> potentialChildren = new ArrayList<>();
            alphabet.chars().mapToObj(c -> (char) c).forEach(potentialChildren::add);
            
            for (String word : validWords) {
                if (word.startsWith(node.getSubstring()) && word.length() > substring.length()) {
                    char letterChild = word.charAt(substring.length());
                    
                    if (potentialChildren.contains(letterChild)) {
                        TrieNode child = new TrieNode(letterChild, substring + letterChild);
                        node.addChild(child);
                        nodeQueue.add(child);
                        potentialChildren.remove((Character) letterChild);
                    }
                    
                    if (potentialChildren.isEmpty()) {
                        break;
                    }
                }
            }
        }
        
        return root;
    }
    
    //
    private class TrieNode {
        private final char letter;
        private final String substring;
        private final ArrayList<TrieNode> children = new ArrayList<>();
        
        public TrieNode(char letter, String substring) {
            this.letter = letter;
            this.substring = substring;
        }
        
        public char getLetter() {
            return letter;
        }
        
        public String getSubstring() {
            return substring;
        }
        
        public void addChild(TrieNode child) {
            children.add(child);
        }
        
        public TrieNode findChild(char letter) {
            TrieNode result = null;
            
            for (TrieNode child : children) {
                if (child.getLetter() == letter) {
                    result = child;
                    break;
                }
            }
            
            return result;
        }
    }
}