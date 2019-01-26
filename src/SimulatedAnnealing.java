import java.util.Random;
import java.util.Vector;

public class SimulatedAnnealing extends Problem {
    private int[] currentState;
    private int[] probabilityArray = new int[1000];
    private float[] schedule = new float[51];

    public SimulatedAnnealing() {
        System.out.println("SimulatedAnnealing");
        fillSchedule();
        currentState = initialState();
        int[] result = search();
        System.out.println("result state is ");
        for (int i = 0; i < result.length ; i++) {
            System.out.print(result[i] + ",");
        }
        System.out.println();
        System.out.println("cost is " + cost(result));
        printInfo();
    }

    private int[] search(){
        int time = 0;
        while (time < schedule.length){
            float temperature = schedule[time];
            if (temperature == 0)
                return currentState;

            Vector<int[]> successors = successors(currentState);
            int index = random.nextInt(successors.size());
            int[] next = successors.get(index);
            visitedNodes++;
            int deltaE = cost(next) - cost(currentState);
            if (deltaE < 0 ) {
                System.arraycopy(next, 0, currentState, 0, next.length);
                exploredNodes++;
            }
            else {
                fillProbabilityArray(deltaE , temperature);
                int check = probabilityArray[random.nextInt(probabilityArray.length)];
                if (check==1) {
                    System.arraycopy(next, 0, currentState, 0, next.length);
                    exploredNodes++;
                }
            }
            time++;
        }
        return null;
    }

    private double calculateProbability(int deltaE , float temperature){
        return Math.exp(((double)deltaE * (-1)/temperature));
    }

    private void fillProbabilityArray(int deltaE , float temperature){
        int num = (int) (calculateProbability(deltaE , temperature) * 1000);
        for (int i = 0; i < num; i++) {
            probabilityArray[i] = 1;
        }
        for (int i = num; i < probabilityArray.length; i++) {
            probabilityArray[i] = 0;
        }
    }

    private void fillSchedule(){
        float i = 5;
        for (int j = 0; j< schedule.length; j++) {
            schedule[j] = i;
            if (i>=0.1)
                i-=0.1;
        }
        schedule[50] = 0;
    }
    private void printInfo(){
        System.out.println("num of visited nodes is "+ visitedNodes);
        System.out.println("num of expanded nodes is "+ exploredNodes);
    }
}