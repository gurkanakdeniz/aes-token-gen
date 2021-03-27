package aes.token.gen;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private SecretKeySpec secretKey;
    private byte[] key;
    
    private static final String SHA = "SHA-1";
    private static final String AES = "AES";

    private static final String UTF8 = "UTF-8";
    private static final String ALG = "AES/ECB/PKCS5PADDING";

    public AES(String secret) throws Exception {
        super();
        setKey(secret);
    }

    private void setKey(String secret) throws Exception {
        MessageDigest sha = null;
        try {
            key = secret.getBytes(UTF8);
            sha = MessageDigest.getInstance(SHA);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, AES);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String encrypt(String text, String secret) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(UTF8)));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String decrypt(String text, String secret) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
