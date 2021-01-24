package rsa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RSAKeyIO {

    private final String filePath;

    public RSAKeyIO(String filePath) {
        this.filePath = addSlashIfMissing(filePath);
    }

    public RSAKeyIO() {
        this.filePath = "";
    }

    /**
     * Writes a key pair to a file
     * @param fileName The name of the key
     * @param key The key pair
     */
    public void write(String fileName, RSAKeyPair key) throws IOException {
        writePrivateKey(fileName, key.getPrivateKey());
        writePublicKey(fileName, key.getPublicKey());
    }

    /**
     * Reads and returns a single key from file
     * @param name The name of the key
     * @return the key from the specified file
     */
    public RSAKey readKey(String name) throws IOException, ClassNotFoundException {
        var fileInputStream = new FileInputStream(filePath + name);
        var objectInputStream = new ObjectInputStream(fileInputStream);
        RSAKey key = (RSAKey) objectInputStream.readObject();
        objectInputStream.close();
        return key;
    }

    private void writePrivateKey(String name, RSAKey key) throws IOException {
        var fileOutputStream = new FileOutputStream(filePath + privateKeyFullName(name));
        var objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(key);
        objectOutputStream.close();
    }

    private void writePublicKey(String name, RSAKey key) throws IOException {
        var fileOutputStream = new FileOutputStream(filePath + publicKeyFullName(name));
        var objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(key);
        objectOutputStream.close();
    }

    private String addSlashIfMissing(String path) {
        if (!path.matches("/$"))
            return path + "/";

        return path;
    }

    private String privateKeyFullName(String name) {
        if (!name.matches(String.format("^.{%s}_priv\\.key$", name.length())))
            return name + "_priv.key";

        return name;
    }

    private String publicKeyFullName(String name) {
        if (!name.matches(String.format("^.{%s}_pub\\.key$", name.length())))
            return name + "_pub.key";

        return name;
    }
}
