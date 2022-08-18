package primeGenerator;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

class PrimeGenerator implements Callable<BigInteger> {
    private BigInteger seed;

    public PrimeGenerator(BigInteger seed) {
        this.seed = seed;
    }

    @Override
    public BigInteger call() throws Exception {
        return seed.nextProbablePrime();
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        //The collection where all my prime numbers will be stored
        PrimeCollection primeCollection = new PrimeCollection();

        //The range in which the prime numbers will be generated
        BigInteger startNumber = new BigInteger(scanner.nextLine());
        BigInteger endNumber = new BigInteger(scanner.nextLine());

        ExecutorService executorService = Executors.newCachedThreadPool();

        //Check the time frame
        Long currentTimeMillisStart = System.currentTimeMillis();

        //Execution process...
        Future<BigInteger> future = executorService.submit(new PrimeGenerator(startNumber));
        BigInteger seed = future.get();
        while (seed.compareTo(endNumber) < 0) {
            executorService.execute(new PrimeCollector(primeCollection, seed));
            future = executorService.submit(new PrimeGenerator(seed));
            seed = future.get();
        }
        executorService.shutdownNow();

        Long currentTimeMillisEnd = System.currentTimeMillis();

        //Print the result
        System.out.println("Prime numbers are: ");
        List<BigInteger> result = primeCollection.getPrimes();
        for (BigInteger bigInteger : result) {
            System.out.println(bigInteger);
        }

        //Print the time required for the execution process
        System.out.println("Time taken : " + (currentTimeMillisEnd - currentTimeMillisStart) + " ms.");
    }
}
