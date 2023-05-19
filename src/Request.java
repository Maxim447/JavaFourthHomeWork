public class Request {
    private int callingFloor;
    private State state;

    public Request(int callingFloor, State state) {
        this.callingFloor = callingFloor;
        this.state = state;
    }

    public int getCallingFloor() {
        return callingFloor;
    }

    public void setCallingFloor(int callingFloor) {
        this.callingFloor = callingFloor;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
