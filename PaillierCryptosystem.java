package paillersystem;

import java.math.BigInteger;
import java.util.*;

public class PaillierCryptosystem {

    BigInteger p, q, lamda, g, miu;
    BigInteger n, nn;
    List<BigInteger> message;
    List<BigInteger> cipher;
    Random random;

    private void generateKey()
    {
        PrimeNumberGenerator png = new PrimeNumberGenerator();

        p = png.generatePrime();

        while ((q = png.generatePrime()).compareTo(p) == 0){}

        n = p.multiply(q);

        BigInteger ax = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        while (n.gcd(ax).compareTo(BigInteger.ONE) != 0)
        {
            p = png.generatePrime();

            while ((q = png.generatePrime()).compareTo(p) == 0){}

            n = p.multiply(q);

            ax = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        }

        lamda = ax.divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));

        random = new Random();

        g = BigInteger.valueOf(random.nextInt(150));

        nn = new BigInteger(n.multiply(n).toString());

        while (g.compareTo(nn) >= 0 || g.gcd(nn).compareTo(BigInteger.ONE) != 0)
            g = BigInteger.valueOf(random.nextInt(150));

        BigInteger a = g.modPow(lamda, nn).subtract(BigInteger.ONE).divide(n);

        while (a.gcd(n).compareTo(BigInteger.ONE) != 0)
        {
            while (g.gcd(nn).compareTo(BigInteger.ONE) != 0)
                g = BigInteger.valueOf(random.nextInt(150));

            a = g.modPow(lamda, nn).subtract(BigInteger.ONE).divide(n);
        }

        miu = a.modPow(BigInteger.valueOf(-1), n);
    }

    PaillierCryptosystem()
    {
        this.generateKey();
        message = new ArrayList<BigInteger>();
        cipher = new ArrayList<BigInteger>();
    }

    List<BigInteger> encrypt()
    {
        return Collections.unmodifiableList(cipher);
    }

    List<BigInteger> encrypt(String m)
    {
        BigInteger r;
        for (int i = 0; i < m.length(); ++i)
        {
            r= BigInteger.valueOf(random.nextInt(n.intValue() % Integer.MAX_VALUE));
            message.add(BigInteger.valueOf(m.charAt(i)));
            cipher.add(g.pow(m.charAt(i)).multiply(r.pow(n.intValue())).mod(nn));
        }

        return Collections.unmodifiableList(cipher);
    }

    List<BigInteger> decrypt(List<BigInteger> c)
    {
        BigInteger a;
        List<BigInteger> m = new ArrayList<BigInteger>();

        for (BigInteger bi : c)
        {
            a = bi.modPow(lamda, nn).subtract(BigInteger.ONE).divide(n);
            m.add(a.multiply(miu).mod(n));
        }

        return m;
    }

    List<BigInteger> decrypt()
    {
        return Collections.unmodifiableList(message);
    }
}
