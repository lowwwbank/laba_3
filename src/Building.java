import java.util.Random;

public class Building extends Thread {
    Lift first, second;
    private final int HEIGHT;
    Random rand;
    Building(Lift first, Lift second, int height) {
        this.first = first;
        this.second = second;
        HEIGHT = height;
        rand = new Random(HEIGHT);
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            int fromFloor = rand.nextInt(HEIGHT) + 1;
            int toFloor = rand.nextInt(HEIGHT) + 1;
            while (fromFloor == toFloor) {
                toFloor = rand.nextInt(HEIGHT) + 1;
            }
            Passenger passenger = new Passenger(fromFloor, toFloor);
            chooseLift(passenger);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Люди ушли спать");
                break;
            }
        }
    }

    private void chooseLift(Passenger passenger) {
        Lift closestElevator;
        if (first.direction.equals(Direction.STAY) && second.direction.equals(Direction.STAY)) {
            if (Math.abs(first.currentFloor - passenger.from) < Math.abs(second.currentFloor - passenger.from)) {
                closestElevator = first;
            } else {
                closestElevator = second;
            }
        }
        else if (!first.direction.equals(Direction.STAY) && !second.direction.equals(Direction.STAY)) {
            boolean isPassengerAndFirstElevatorGoingTheSameDirection = ((first.direction == Direction.UP && passenger.from > first.currentFloor)
                    || (first.direction == Direction.DOWN && passenger.from < first.currentFloor));
            boolean isPassengerAndSecondElevatorGoingTheSameDirection = ((second.direction == Direction.UP && passenger.from > second.currentFloor)
                    || (second.direction == Direction.DOWN && passenger.from < second.currentFloor));

            if (isPassengerAndFirstElevatorGoingTheSameDirection && !isPassengerAndSecondElevatorGoingTheSameDirection) {
                closestElevator = first;
            } else if (!isPassengerAndFirstElevatorGoingTheSameDirection && isPassengerAndSecondElevatorGoingTheSameDirection) {
                closestElevator = second;
            } else {
                int distanceToFirst = Math.abs(first.currentFloor - passenger.from);
                int distanceToSecond = Math.abs(second.currentFloor - passenger.from);
                closestElevator = distanceToFirst < distanceToSecond ? first : second;
            }
        }

        else if (first.direction.equals(Direction.STAY) && !second.direction.equals(Direction.STAY)) {
            closestElevator = first;
        }
        else {
            closestElevator = second;
        }

        callElevator(passenger, closestElevator);
    }

    public void callElevator(Passenger passenger, Lift e) {
        e.out.add(passenger);
        System.out.printf("%s | кнопка %s нажата на %d этаже\n", e.name, passenger.direction, passenger.from);
    }
}
