package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class RSAKeyGenerator {

    public static RSAKeyPair generateKeys(int bitLength) {
        BigInteger p = generatePrime(bitLength / 2);
        BigInteger q = generatePrime(bitLength / 2);
        BigInteger n = p.multiply(q);
        BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // φ(n)=(p-1)(q-1)
        BigInteger e = generateE(phiN);
        BigInteger d = e.modInverse(phiN);

        var publicKey = new RSAKey(e, n);
        var privateKey = new RSAKey(d, n);

        return new RSAKeyPair(publicKey, privateKey);
    }

    private static BigInteger generatePrime(int bitLength) {
        return new BigInteger(bitLength, 100, new SecureRandom());
    }

    private static BigInteger generateE(BigInteger phiN) {
        BigInteger e = BigInteger.valueOf(3);

        while (phiN.gcd(e).intValue() > 1) {
            e = e.add(BigInteger.valueOf(2));
        }

        return e;
    }
}
