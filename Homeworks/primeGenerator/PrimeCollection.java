package primeGenerator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimeCollection {

    private List<BigInteger> primes;

    public PrimeCollection() {
        synchronized (this) {
            primes = new ArrayList<>();
        }
    }

    //You can take an unmodifiable collection of the generated primes
    public synchronized List<BigInteger> getPrimesUnmodifiable() {
        return Collections.unmodifiableList(this.primes);
    }

    public synchronized List<BigInteger> getPrimes() {
        return primes;
    }

    public void addPrime(BigInteger prime) {
        synchronized (this) {
            primes.add(prime);
            //Let's see which thread is doing this
            System.out.println(Thread.currentThread().getName());
        }
    }

}