package primeGenerator;

import java.math.BigInteger;

public class PrimeCollector implements Runnable {

    private final PrimeCollection primeCollection;
    private final BigInteger prime;

    public PrimeCollector(PrimeCollection primeCollection, BigInteger prime) {
        synchronized (this) {
            this.primeCollection = primeCollection;
            this.prime = prime;
        }
    }

    @Override
    public void run() {
        synchronized (this) {
            primeCollection.addPrime(prime);
        }
    }
}
