import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Lift extends Thread {
    public int currentFloor = 1;
    public Direction direction = Direction.STAY;
    BlockingQueue<Passenger> in = new LinkedBlockingQueue<>();
    BlockingQueue<Passenger> out = new LinkedBlockingQueue<>();
    public String name;
    public Lift(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

            chooseDirection();
            enterLift();
            exitLift();

            liftSleep(1000);
        }
        System.out.println(name + " | остановлен");
    }

    private void liftSleep(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void exitLift() {
        int counter = 0;
        for (Passenger p : in) {
            if (p.to == currentFloor) {
                boolean ignored = in.remove(p);
                counter++;
            }
        }
        if (counter == 1) {
            System.out.printf("%s | %d пассажир вышел, на %d этаже\n", name, counter, currentFloor);
        } else if (counter != 0){
            System.out.printf("%s | %d пассажиров вышло, на %d этаже\n", name, counter, currentFloor);
        }

    }
    private void enterLift() {
        int counter = 0;
        for (Passenger p : out) {
            if (p.from == currentFloor) {
                boolean ignored = out.remove(p);
                in.add(p);
                counter++;
            }
        }
        if (counter == 1) {
            System.out.printf("%s | %d пассажир вошел на %d этаже\n", name, counter, currentFloor);
        } else if (counter != 0){
            System.out.printf("%s | %d пассажиров вошло на %d этаже\n", name, counter, currentFloor);
        }
    }

    public void step(int floor) {
        if (floor > currentFloor) {
            direction = Direction.UP;
            currentFloor += 1;
            System.out.printf("%s | движение вверх на этаж %d\n", name, currentFloor);
        } else if (floor < currentFloor) {
            direction = Direction.DOWN;
            currentFloor -= 1;
            System.out.printf("%s | движение вниз на этаж %d\n", name, currentFloor);
        }
        liftSleep(500);
    }
    private void chooseDirection() {
        if (!in.isEmpty()) {
            step(in.peek().to);
        } else while (out.isEmpty()) {
            direction = Direction.STAY;
            liftSleep(500);
            if (Thread.currentThread().isInterrupted()){
                break;
            }
        }
        if (!out.isEmpty() && in.isEmpty()) {
            step(out.peek().from);
        }
    }
}
