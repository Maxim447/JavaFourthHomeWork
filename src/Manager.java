import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Manager implements Runnable {
    private static final Manager manager = new Manager();
    ConcurrentLinkedQueue<Request> requests = new ConcurrentLinkedQueue<>();
    Elevator elevator1 = new Elevator(1, requests, Main.FLOORS);
    Elevator elevator2 = new Elevator(2, requests, Main.FLOORS);

    Thread elevator1Thread = new Thread(elevator1);
    Thread elevator2Thread = new Thread(elevator2);

    Boolean isWorking = true;
    ;

    public static Manager getManager() {
        return manager;
    }

    @Override
    public void run() {
        elevator1Thread.start();
        elevator2Thread.start();
        try {
            elevator1Thread.join();
            elevator2Thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    public synchronized int bestElevator(Request request) {
        boolean res = Math.abs(elevator1.getCurrentFloor() - request.getCallingFloor()) < Math.abs(elevator2.getCurrentFloor() - request.getCallingFloor());
        if (res) {
            elevator1.setCallingFloor(request.getCallingFloor());
            elevator1.setState(request.getState());
            return elevator1.getId();
        } else {
            elevator2.setCallingFloor(request.getCallingFloor());
            elevator2.setState(request.getState());
            return elevator2.getId();
        }
    }

    public boolean isEmpty() {
        return requests.isEmpty();
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public Boolean getWorking() {
        return isWorking;
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    public void removeRequest(Request request) {
        requests.remove(request);
    }

    public boolean containsRequest(Request request) {
        System.out.println(requests.contains(request));
        return requests.contains(request);
    }
}
