import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static int FLOORS = 0;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите кол-во заявок: ");
        int quantity = scanner.nextInt();
        System.out.print("Введити интревал появления заявок: ");
        int interval = scanner.nextInt();
        System.out.print("Введити кол-во этажей в доме: ");
        FLOORS = scanner.nextInt();

        ConcurrentLinkedQueue<Request> requests = new ConcurrentLinkedQueue<>();

        Thread managerThread = new Thread(Manager.getManager());
        Generator generator = new Generator(interval, quantity, FLOORS, requests);
        Thread requestThread = new Thread(generator);

        managerThread.start();
        requestThread.start();

        try {
            managerThread.join();
            requestThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
