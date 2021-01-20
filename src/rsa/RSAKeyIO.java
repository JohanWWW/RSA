package rsa;

import java.io.*;

public class RSAKeyIO {

    private final String filePath;

    public RSAKeyIO(String filePath) {
        this.filePath = addSlashIfMissing(filePath);
    }

    public RSAKeyIO() {
        this.filePath = "";
    }

    public void write(String fileName, RSAKeyPair key) throws IOException {
        writePrivateKey(fileName, key.getPrivateKey());
        writePublicKey(fileName, key.getPublicKey());
    }

    public RSAKeyPair read(String name) throws IOException, ClassNotFoundException {
        RSAKey publicKey = readPublicKey(name);
        RSAKey privateKey = readPrivateKey(name);

        return new RSAKeyPair(publicKey, privateKey);
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

    private RSAKey readPrivateKey(String name) throws IOException, ClassNotFoundException {
        var fileInputStream = new FileInputStream(filePath + privateKeyFullName(name));
        var objectInputStream = new ObjectInputStream(fileInputStream);
        RSAKey key = (RSAKey) objectInputStream.readObject();
        objectInputStream.close();
        return key;
    }

    private RSAKey readPublicKey(String name) throws IOException, ClassNotFoundException {
        var fileInputStream = new FileInputStream(filePath + publicKeyFullName(name));
        var objectInputStream = new ObjectInputStream(fileInputStream);
        RSAKey key = (RSAKey) objectInputStream.readObject();
        objectInputStream.close();
        return key;
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
