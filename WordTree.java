import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WordTree {
    private final SubstringNode root;
    
    public WordTree(String[] validWords) {
        this.root = buildSubstringTree(validWords);
    }
    
    //
    public boolean isValidWord(String word) {
        boolean isValid = false;
        
        if (word.length() == 5) {
            SubstringNode node = root;
            
            for (char c : word.toCharArray()) {
                isValid = false;
                
                for (SubstringNode child : node.getChildren()) {
                    if (child.getLetter() == c) {
                        node = child;
                        isValid = true;
                        break;
                    }
                }
                
                if (!isValid) {
                    break;
                }
            }
        }
        
        return isValid;
    }
    
    //
    private SubstringNode buildSubstringTree(String[] validWords) {
        Queue<SubstringNode> nodeQueue = new LinkedList<>();
        SubstringNode rootNode = new SubstringNode('\0', "");
        SubstringNode node = rootNode;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int depth = 0;
        
        while (depth < 5) {
            Queue<Character> potentialChildren = new LinkedList<>();
            alphabet.chars().mapToObj(c -> (char) c).forEach(potentialChildren::add);

            for (String word : validWords) {
                if (word.startsWith(node.getSubstring())) {
                    for (int i = 0; i < potentialChildren.size(); i++) {
                        char c = potentialChildren.remove();
                        String substring = node.getSubstring() + c;
                        
                        if (word.charAt(depth) == c) {
                            SubstringNode child = new SubstringNode(c, substring);
                            node.addChild(child);
                            nodeQueue.add(child);
                            break;
                        }
                        else {
                            potentialChildren.add(c);
                        }
                    }
                    
                    if (potentialChildren.isEmpty()) {
                        break;
                    }
                }
            }
            
            node = nodeQueue.remove();
            depth = node.getDepth();
        }
        
        return rootNode;
    }
    
    //
    private class SubstringNode {
        private final char letter;
        private final String substring;
        private final int depth;
        private final ArrayList<SubstringNode> children = new ArrayList<>();
        
        public SubstringNode(char letter, String substring) {
            this.letter = letter;
            this.substring = substring;
            this.depth = substring.length();
        }
        
        public char getLetter() {
            return letter;
        }
        
        public String getSubstring() {
            return substring;
        }
        
        public int getDepth() {
            return depth;
        }
        
        public ArrayList<SubstringNode> getChildren() {
            return children;
        }
        
        public void addChild(SubstringNode child) {
            children.add(child);
        }
    }
}