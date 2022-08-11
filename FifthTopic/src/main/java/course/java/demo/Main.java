package course.java.demo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int start = 10;
        int end = 20;
        int[] values = IntStream.rangeClosed(start, end - 1).toArray();
        List<Integer> integers = new ArrayList<>();
        BigInteger primeNumberGenerator = new BigInteger(String.valueOf(0));
        for (int value : values) {
            if (primeNumberGenerator.isProbablePrime(value)){
                integers.add(value);
            }
        }

        for (int i = start; i < end; i++) {
            integers.add(new BigInteger(String.valueOf(i)).nextProbablePrime().intValue());
        }
        for (Integer integer : integers) {
            System.out.println(integer);
        }

    }
}

class PrimeGenerator implements Callable<BigInteger>{

    private BigInteger primeNumberGenerator;
    private int start;
    private int end;

    public PrimeGenerator(int start, int end) {
        this.primeNumberGenerator = new BigInteger("s");
        this.start = start;
        this.end = end;
    }

    @Override
    public BigInteger call() throws Exception {
        for (int i = 0; i < end; i++) {

        }
        return null;
    }
}