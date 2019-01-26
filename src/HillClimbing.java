import java.util.Scanner;
import java.util.Vector;

public class HillClimbing extends Problem{
    private int[] currentState;

    public HillClimbing(int type) {
        System.out.println("HillClimbing");
        currentState = initialState();
        int[] res = null;
        Scanner n = new Scanner(System.in);
        switch (type) {
            case 1:
                res = stochasticSearch();
                break;
            case 2:
                System.out.println("Max Num Of Sideways?");
                int max = n.nextInt();
                res = normalSearch(max);
                break;
            case 3:
                System.out.println("Num Of Restarts?");
                res = randomRestart(Integer.parseInt(n.nextLine()));
                break;
            case 4:
                res = firstChoiceSearch();
                break;
        }
        for (int i = 0; i < res.length ; i++) {
            System.out.print(res[i] + ",");
        }
        System.out.println();
        System.out.println("cost is " + cost(res));
        printInfo();
    }

    private int[] firstChoiceSearch(){
        while (true) {
            int[] next = firstChoice();
            if (next==null){
                return currentState;
            }
            int currentCost = cost(currentState);
            int nextCost = cost(next);
            if (nextCost > currentCost)
                return currentState;
            if (nextCost < currentCost){
                System.arraycopy(next, 0, currentState, 0, next.length);
                exploredNodes++;
            }
            else {
                return currentState;
            }
        }
    }

    private int[] firstChoice(){
        Vector<int[]> successors = successors(currentState);
        int costN = cost(currentState);
        for (int i = 0; i < successors.size(); i++) {
            visitedNodes++;
            if (cost(successors.get(i)) < costN) {
                return successors.get(i);
            }
        }
        return null;
    }

    private int[] randomRestart(int num) {
        for (int i = 0; i < num; i++) {
            int[] res = normalSearch(10);
            if (cost(res)==0)
                return res;
            if (cost(res) < cost(currentState)) {
                currentState = res;
                exploredNodes++;
            }
        }
        return currentState;
    }

    private int[] stochasticSearch(){
        while (true){
            int currentCost = cost(currentState);
            Vector<int[]> succ = betterNeighbors();
            if (succ.size() == 0)
                return currentState;
            int[] next = succ.get(random.nextInt(succ.size()));
            int nextCost = cost(next);
            if (nextCost > currentCost)
                return currentState;
            else if (nextCost < currentCost){
                System.arraycopy(next, 0, currentState, 0, next.length);
                exploredNodes++;
            }
            else {
                return currentState;
            }
        }
    }

    private Vector<int[]> betterNeighbors(){
        Vector<int[]> n = new Vector<>();
        int costN = cost(currentState);
        Vector<int[]> successors = successors(currentState);
        visitedNodes+=successors.size();
        for (int i = 0; i < successors.size(); i++) {
            if (cost(successors.get(i)) < costN){
                n.add(successors.get(i));
            }
        }
        return n;
    }

    private int[] normalSearch(int maxSideWay){
        int sideway = 0;
        while (true) {
            int[] next = bestNeighbor();
            if (cost(next) == 0)
                return next;
            int currentCost = cost(currentState);
            int nextCost = cost(next);
            if (nextCost > currentCost)
                return currentState;
            else if (nextCost < currentCost){
                System.arraycopy(next, 0, currentState, 0, next.length);
                exploredNodes++;
                sideway = 0;
            }
            else {
                if (maxSideWay > 0) {
                    if (sideway == maxSideWay)
                        return currentState;
                    System.arraycopy(next, 0, currentState, 0, next.length);
                    exploredNodes++;
                    sideway++;
                }else {
                    return currentState;
                }
//                return currentState;
            }

        }
    }

    private int[] bestNeighbor(){
        Vector<int[]> successors = successors(currentState);
        visitedNodes+=successors.size();
        int min = 999999999;
        int[] minArr = null;
        for (int i = 0; i < successors.size(); i++) {
            int cost = cost(successors.get(i));
            if (cost < min) {
                min = cost;
                minArr = successors.get(i);
            }
        }
        return minArr;
    }

    private void printInfo(){
        System.out.println("num of visited nodes is "+ visitedNodes);
        System.out.println("num of expanded nodes is "+ exploredNodes);
    }
}
