package rsa;

import java.math.BigInteger;

public class RSAKey implements java.io.Serializable {
    private static final long serialVersionUID = 4L;

    private final BigInteger key;
    private final BigInteger n;

    /**
     * Constructs a new {@link RSAKey} object
     * @param key the value of either <i>e (for public keys)</i> or <i>d (for private keys)</i>
     * @param n the value of <i>n</i>
     */
    public RSAKey(BigInteger key, BigInteger n) {
        this.key = key;
        this.n = n;
    }

    public BigInteger getKey() {
        return key;
    }

    public BigInteger getN() {
        return n;
    }
}
