package course.java.mutithreading.simple;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class RocketCountdown implements Runnable {

    private int rocketNumber;

    public RocketCountdown(int rocketNumber) {
        this.rocketNumber = rocketNumber;
    }

    @Override
    public void run() {
        try {
            for (int i = 10; i > 0; i--) {
                System.out.printf("Rocket %d: Countdown %d [%s]%n", rocketNumber, i,
                        Thread.currentThread().getName());
                Thread.sleep(100);
            }
            System.out.printf("Rocket %d: Liftoff! [%s]%n", rocketNumber,
                    Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println("The thread was interrupted - finishing.");
        }
    }
}

public class RocketsCountdown {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        //ExecutorService executorService = Executors.newFixedThreadPool(4);
        //ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            //executorService.scheduleWithFixedDelay(new RocketCountdown(i), 2000, 1000, TimeUnit.MILLISECONDS);
            executorService.submit(new RocketCountdown(i));
            //new Thread(new RocketCountdown(i)).start(); //It starts the counting for every rocket in parallel
            //new RocketCountdown(i).run(); //it starts the rocket counting 1 by 1
        }
        Thread.sleep(10000);
        executorService.shutdownNow();
    }
}

