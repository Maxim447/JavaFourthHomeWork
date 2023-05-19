import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Elevator implements Runnable {
    private int id;

    private int currentFloor;
    private int callingFloor;
    private State state;

    private ConcurrentLinkedQueue<Request> requests;

    private int floors;

    public Elevator(int id, ConcurrentLinkedQueue<Request> requests, int floors) {
        this.id = id;
        this.currentFloor = 1;
        this.state = State.SLEEP;
        this.requests = requests;
        this.floors = floors;
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public State getState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCallingFloor() {
        return callingFloor;
    }

    public void setCallingFloor(int callingFloor) {
        this.callingFloor = callingFloor;
    }

    public ConcurrentLinkedQueue<Request> getRequests() {
        return requests;
    }

    public void setRequests(ConcurrentLinkedQueue<Request> requests) {
        this.requests = requests;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public synchronized void moveElevator(int callingFloor) {
        State elevatorState = currentFloor < callingFloor ? State.UP : State.DOWN;
        while (currentFloor != callingFloor) {
            System.out.println("Лифт №" + id + " движется до " + callingFloor + " этажа " + elevatorState + " (текущий этаж " + currentFloor + ")");
            if (elevatorState == State.UP) {
                for (Request request : requests) {
                    if (request.getCallingFloor() == currentFloor && request.getState() == elevatorState) {
                        requests.remove(request);
                        System.out.println("Пассажир зашел в лифт№" + id + " на " + currentFloor + " этаже");
                    }
                }
                currentFloor++;
            } else {
                for (Request request : requests) {
                    if (request.getCallingFloor() == currentFloor && request.getState() == elevatorState) {
                        requests.remove(request);
                        System.out.println("Пассажир зашел в лифт№" + id + " на " + currentFloor + " этаже");
                    }
                }
                currentFloor--;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void run() {
        Manager manager = Manager.getManager();
        while (!manager.isEmpty() || manager.getWorking()) {
            while (manager.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }

            if (!requests.isEmpty() && manager.bestElevator(requests.peek()) == this.id) {
                Request request = requests.poll();
                assert request != null;
                moveElevator(request.getCallingFloor());
                System.out.println("Лифт №" + id + " прибыл на " + request.getCallingFloor() + " этаж и взял пассажира");
            } else {
                continue;
            }
        }
    }
}
