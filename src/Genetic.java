import java.util.Vector;

public class Genetic extends Problem{
    private int populationSize;
    private int tournamentSize;
    private int numberOfGenerations;
    private double mutationRate;
    private Vector<int[]> population;

    public Genetic(int populationSize, int tournamentSize, int numberOfGenerations,double mutationRate) {
        System.out.println("Genetic");
        this.populationSize = populationSize;
        this.tournamentSize = tournamentSize;
        this.numberOfGenerations = numberOfGenerations;
        this.mutationRate = mutationRate;
        population = new Vector<>(populationSize);
        fillPopulation();
        recurse();
    }

    private void recurse(){
        for (int i = 0; i < numberOfGenerations; i++) {
            parentSelection();
            makeChildren();
            Vector<int[]> newPop = mutate();
            for (int j = 0; j < populationSize; j++) {
                population.setElementAt(newPop.get(j) , j);
            }
            printInformation(population , i);
        }
    }

    private void printInformation(Vector<int[]> population, int i){
        System.out.println("Generation number "+ i);
        int min = cost(population.get(0));
        int max = cost(population.get(0));
        int sum = 0;
        for (int j = 1; j < population.size(); j++) {
            int fitness = cost(population.get(j));
            if (fitness < min)
                min = fitness;
            if (fitness > max)
                max = fitness;
            sum+=fitness;
        }
        System.out.println("Max Cost is " + max);
        System.out.println("Min Cost is " + min);
        System.out.println("Average Cost is " + sum/population.size());
    }

    private void fillPopulation(){
        for (int i = 0; i < populationSize; i++) {
            int[] chromosome = initialState();  // a random coloring of graph
            population.add(chromosome);
        }
    }

    private int fitnessFunction(int[] state){  //aim: minimize fitness
        int sum = 0;
        for (int i = 0; i < graph.size(); i++) {
            Vector<problem.Node> adj = graph.get(i).adjNodes;
            for (int j = 0 ; j < adj.size() ; j++) {
                int v1 = graph.get(i).number;
                int v2 = adj.get(j).number;
                if (v1 > v2 && state[v1]==state[v2]){
                    sum+=1;
                }
            }
        }
        return sum;
    }

    private Vector<int[]> parentSelection(){
        Vector<int[]> populationCopy = new Vector<>();
        populationCopy.addAll(population);
        Vector<int[]> tournamentMembers = new Vector<>(tournamentSize);
        Vector<int[]> parents = new Vector<>(populationSize/tournamentSize);
        int k = 0;
        while (k < populationSize/tournamentSize ) {
            for (int i = 0; i < tournamentSize; i++) {
                if (populationCopy.size() > 0) {
                    int rand = random.nextInt(populationCopy.size());
                    int[] member = populationCopy.get(rand);
                    tournamentMembers.add(member);
                    populationCopy.remove(member);
                }
            }
            int[] best = null;
            for (int i = 0; i < tournamentMembers.size(); i++) {    // best between members
                int[] ind = tournamentMembers.get(i);
                if (best == null || fitnessFunction(ind) < fitnessFunction(best))
                    best = ind;
            }
            parents.add(best);
            k++;
        }
//        printState(parents);
        return parents;
    }

    private Vector<int[]> makeChildren(){
        Vector<int[]> parentsCopy = new Vector<>();
        Vector<int[]> newChromosomes = new Vector<>();
        parentsCopy.addAll(parentSelection());
//        System.out.println("size is "+ parentsCopy.size());
        for (int i = 0; i < populationSize; i++) {
            int[] p1 = parentsCopy.get(random.nextInt(parentsCopy.size()));
            int[] p2 = parentsCopy.get(random.nextInt(parentsCopy.size()));
            newChromosomes.add(crossOver(p1, p2));
        }
//        printState(newChromosomes);    //size of children = populationSize
        return newChromosomes;
    }

    private int[] crossOver(int[] p1, int[] p2){
        int thresh = random.nextInt(p1.length);
        int[] newChromosome = new int[p1.length];
        for (int i = 0; i < thresh ; i++) {
            newChromosome[i] = p1[i];
        }
        for (int i = thresh; i < p2.length; i++) {
            newChromosome[i] = p2[i];
        }
        return newChromosome;
    }

    private Vector<int[]> mutate(){
        Vector<int[]> children = makeChildren();
        int n = population.get(0).length;   // nodes in the graph
        int mutatedGenomes = (int) (populationSize * n * mutationRate);
        for (int i = 0; i < mutatedGenomes; i++) {
            int randInPop = random.nextInt(children.size());
            int[] randomMember = children.get(randInPop);
            int randInMem = random.nextInt(randomMember.length);
            randomMember = newValue(randomMember , randInMem);
            population.setElementAt(randomMember , randInPop);
        }
//        printState(children);
        return children;
    }

    private int[] newValue(int[] member , int index){
        int[] colors = new int[numOfColors - 1];
        int count = 0;
        for (int i = 0; i < numOfColors; i++) {
            if (i != member[index]){
                colors[count] = i;
                count++;
            }
        }
        member[index] = colors[random.nextInt(colors.length)];
        return member;
    }

    void printState(Vector<int[]> vec){
        for (int i = 0; i < vec.size(); i++) {
            for (int j = 0; j < vec.get(i).length; j++) {
                System.out.print(vec.get(i)[j] + ",");
            }
            System.out.println();
        }
    }

}
