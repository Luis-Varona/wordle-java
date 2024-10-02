import java.util.ArrayList;

public class SubstringNode {
    //
    private final char letter;
    private final String substring;
    private final int depth;
    private final ArrayList<SubstringNode> children;
    
    public SubstringNode(char letter, String substring) {
        this.letter = letter;
        this.substring = substring;
        this.depth = substring.length();
        this.children = new ArrayList<>();
    }
    
    //
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
    
    //
    public void addChild(SubstringNode child) {
        children.add(child);
    }
    
    public void removeChild(SubstringNode child) {
        children.remove(child);
    }
}
