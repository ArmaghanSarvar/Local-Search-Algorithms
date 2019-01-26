import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Problem {
    protected int numOfEdges;
    protected Vector<problem.Node> graph = new Vector<>();
    private Scanner scanner = new Scanner(System.in);
    private String fileName;
    protected Random random;
    protected int visitedNodes;
    protected int exploredNodes;
    protected int numOfColors;

    public Problem() {
        numOfColors = 4;
        random = new Random();
        initializer();
        readFile();
//        printN();
    }

    private void initializer(){
        for (int i = 0; i < 30 ; i++) {
            graph.add(new problem.Node(i));
        }
    }

    private void readFile(){
        System.out.println("enter the file name please");
        fileName = scanner.nextLine();
        File file = new File(fileName);
        String[] edge = new String[2];
        try {
            Scanner filereader = new Scanner(file);
            do {
                String s = filereader.nextLine();
                numOfEdges++;
                edge[0] = s.split(",")[0];
                edge[1] = s.split(",")[1];
                addNeighbors(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
            } while (filereader.hasNext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addNeighbors(int first , int second){
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).number == first) {
                graph.get(i).adjNodes.add(new problem.Node(second));
            }
            else if (graph.get(i).number == second) {
                graph.get(i).adjNodes.add(new problem.Node(first));
            }
        }
    }

    protected int[] initialState(){
        int[] initState = new int[graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            initState[i] = random.nextInt(numOfColors);
        }
        return initState;
    }

    protected Vector<int[]> successors(int[] current){
        Vector<int[]> successors = new Vector<>();
        for (int i = 0; i < current.length; i++) {
            int[] colors = differentColors(current[i]);
            for (int j = 0; j < colors.length; j++) {
                int[] successor = new int[graph.size()];
                System.arraycopy(current, 0, successor, 0, current.length);
                successor[i] = colors[j];
                successors.add(successor);
            }
        }
        return successors;
    }

    private int[] differentColors(int currentColor){
        int[] colors = new int[numOfColors - 1];
        int count = 0;
        for (int i = 0; i < numOfColors; i++) {
            if (i != currentColor){
                colors[count] = i;
                count++;
            }
        }
        return colors;
    }

    protected int cost(int[] state){
        int cost = 0;
        for (int i = 0; i < graph.size(); i++) {
            Vector<problem.Node> adj = graph.get(i).adjNodes;
            for (int j = 0 ; j < adj.size() ; j++) {
                int v1 = graph.get(i).number;
                int v2 = adj.get(j).number;
                if (v1 > v2 && state[v1]==state[v2])
                    cost++;
            }
        }
        return cost;
    }
}