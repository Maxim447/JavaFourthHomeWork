import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Generator implements Runnable {

    private int interval;

    private int quantity;

    private int floors;

    private final ConcurrentLinkedQueue<Request> requests;

    public Generator(int interval, int quantity, int floors, ConcurrentLinkedQueue<Request> requests) {
        this.interval = interval;
        this.quantity = quantity;
        this.floors = floors;
        this.requests = requests;
    }

    @Override
    public void run() {
        Manager manager = Manager.getManager();
        Random random = new Random();
        for (int i = 0; i < this.quantity; i++) {
            int randomStartingFloor = random.nextInt(this.floors) + 1;
            Request request = new Request(randomStartingFloor, random.nextBoolean() ? State.UP : State.DOWN);
            manager.addRequest(request);
            System.out.println(request.getCallingFloor() + " " + request.getState());
            try {
                Thread.sleep(this.interval);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
        manager.setIsWorking(false);
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public ConcurrentLinkedQueue<Request> getRequests() {
        return requests;
    }

}
