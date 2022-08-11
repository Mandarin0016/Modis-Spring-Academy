package course.java.mutithreading.simple;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class ComputeResult implements Callable<String> {

    private String keywords;
    private Random random = new Random();

    public ComputeResult(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String call() throws Exception {
        System.out.printf("Inside method call with keywords: %s [%s]%n", keywords,
                Thread.currentThread().getName());
        Thread.sleep(random.nextInt(1000));
        return String.format("Search result for keywords: %s%n", keywords);
    }
}

public class CallablesDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        //ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        //ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Future<String>> futures = new ArrayList<>();

        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<String> cs = new ExecutorCompletionService(executorService);
        for (int i = 0; i < 10; i++) {
            Future<String> f = cs.submit(new ComputeResult("Ivan" + i));
            //Future<String> f = executorService.submit(new ComputeResult("Ivan" + i));
            futures.add(f);
            //executorService.submit(new RocketCountdown(i));
            //new Thread(new RocketCountdown(i)).start(); //It starts the counting for every rocket in parallel
            //new RocketCountdown(i).run(); //it starts the rocket counting 1 by 1
        }
        for (Future<String> future : futures) {
            String result = cs.take().get();
            //String result = future.get(4000, TimeUnit.MILLISECONDS);
            System.out.println(result);
        }

//        Thread.sleep(10000);
//        executorService.shutdownNow();
    }
}
