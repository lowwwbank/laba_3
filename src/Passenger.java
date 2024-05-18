public class Passenger {
    public final int from;
    public final int to;
    public final Direction direction;

    public Passenger(int from, int to) {
        this.from = from;
        this.to = to;
        if (from > to) direction = Direction.DOWN;
        else direction = Direction.UP;
    }
}
