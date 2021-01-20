package rsa;

/**
 * Represents a tuple of mathematically related keys
 */
public class RSAKeyPair {
    private final RSAKey publicKey;
    private final RSAKey privateKey;

    RSAKeyPair(RSAKey publicKey, RSAKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public RSAKey getPublicKey() {
        return publicKey;
    }

    public RSAKey getPrivateKey() {
        return privateKey;
    }
}
