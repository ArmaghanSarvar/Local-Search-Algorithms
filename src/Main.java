import java.util.Scanner;
import java.util.Vector;

public class Main {
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Main main = new Main();
        main.getInput();
    }

    private void getInput(){
        System.out.println("Enter the Algorithm\n" + "1.SimulatedAnnealing 2.Genetic 3.HillClimbing");
        String s = scanner.nextLine();
        switch (s) {
            case "1":
                new SimulatedAnnealing();
                break;
            case "2":
                System.out.println("Enter the populationSize , tournamentSize , numOfGeneration , mutationRate");
                int first = scanner.nextInt();
                int second = scanner.nextInt();
                int third = scanner.nextInt();
                double fourth =  Double.parseDouble(scanner.nextLine());
                new Genetic(first, second, third, fourth);
                break;
            case "3":
                System.out.println("Enter the Type\n" + "1.stochastic 2.normal 3.randomRestart 4.firstChoice");
                new HillClimbing(Integer.parseInt(scanner.nextLine()));
                break;
        }
    }
}