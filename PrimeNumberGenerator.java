package paillersystem;

import java.math.BigInteger;
import java.util.Random;

public class PrimeNumberGenerator {

    BigInteger generatePrime()
    {
        Random rand = new Random();
        long num = rand.nextInt(150) + 1;

        while (!isPrime(num))
            num = rand.nextInt(150) + 1;

        return new BigInteger(Long.toString(num));
    }

    private boolean isPrime(long num){

        if (num <= 3 || num % 2 == 0)
            return num == 2 || num == 3;

        long divisor = 3;

        while ((divisor <= Math.sqrt(num)) && (num % divisor != 0))
            divisor += 2;

        return num % divisor != 0;
    }
}
