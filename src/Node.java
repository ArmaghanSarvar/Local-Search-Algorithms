package problem;

import java.util.Vector;

public class Node {
    public int number;
    public Vector<Node> adjNodes;

    public Node(int number) {
        adjNodes = new Vector<>();
        this.number = number;
    }
}
