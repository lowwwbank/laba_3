import java.util.Scanner;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите кол-во этажей здания для симуляции:");
        int height = scan.nextInt();
        System.out.println("Введите необходимое время(в секундах) проведения симуляции:\n");
        int time = scan.nextInt();

        Lift lift1 = new Lift( "1 Лифт");
        Lift lift2 = new Lift( "2 Лифт");
        Building building = new Building(lift1, lift2, height);

        lift1.start();
        lift2.start();
        building.start();

        SECONDS.sleep(time);

        lift1.interrupt();
        lift2.interrupt();
        building.interrupt();
    }
}
