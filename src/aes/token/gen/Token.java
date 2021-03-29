package aes.token.gen;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Token {
    private static final String SECRET_KEY = "ssshhhhhhhhhhh!!!!";
    private static final String TOKEN_SALT_PATTERN = ":";

    private static final String UTF8 = "UTF-8";
    private static final String DATE_FORMAT_PATTERN = "MMddyyyyHHmmss";
    private static final String TOKEN_SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private AES aes;
    private String secretKey;
    private String token;
    private String encryptedToken;

    public Token() throws Exception {
        this(SECRET_KEY);
    }

    public Token(String secretKey) throws Exception {
        super();
        this.secretKey = secretKey;
        this.aes = new AES(secretKey);
    }

    public Token(String secretKey, String encryptedToken) throws Exception {
        super();
        this.secretKey = secretKey;
        this.encryptedToken = encryptedToken;
        this.aes = new AES(secretKey);
    }

    private String generateSalt() {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < 8) {
            int index = (int) (rnd.nextFloat() * TOKEN_SALT_CHARS.length());
            salt.append(TOKEN_SALT_CHARS.charAt(index));
        }

        return salt.toString();
    }

    private String salting(String token) throws Exception {
        return token + TOKEN_SALT_PATTERN + generateSalt();
    }

    private String generateToken() throws Exception {
        return new SimpleDateFormat(DATE_FORMAT_PATTERN).format(new Date())
                + UUID.randomUUID().toString().replace("-", "");
    }

    public String token() throws Exception {
        this.token = Base64.getEncoder().encodeToString(salting(generateToken()).getBytes(UTF8));
        return this.token;
    }

    public String encrypt() throws Exception {
        this.encryptedToken = URLEncoder.encode(this.aes.encrypt(this.token, this.secretKey), UTF8);
        return this.encryptedToken;
    }

    public String decrypt() throws Exception {
        this.token = this.aes.decrypt(URLDecoder.decode(this.encryptedToken, UTF8), SECRET_KEY);
        return this.token;
    }

    public String mostSignificant() throws Exception {
        return new String(Base64.getDecoder().decode(this.token), UTF8).split(TOKEN_SALT_PATTERN)[0];
    }

    public String leastSignificant() throws Exception {
        return new String(Base64.getDecoder().decode(this.token), UTF8).split(TOKEN_SALT_PATTERN)[1];
    }

}