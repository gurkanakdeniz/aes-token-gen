package aes.token.gen;

public class Sample {

    public static void main(String[] args) throws Exception {
        
        String SECRET_KEY = "ssshhhhhhhhhhh!!!!";
        
        Token token = new Token(SECRET_KEY);
        
        String tokenString = token.token();
        String encryptedString = token.encrypt();

        System.out.println("TOKEN : " + tokenString);
        System.out.println("MOST  : " + token.mostSignificant());
        System.out.println("LEAST : " + token.leastSignificant());
        System.out.println("");
        System.out.println("ENC   : " + encryptedString);
        System.out.println("");
        
        Token decToken = new Token(SECRET_KEY, encryptedString);
        String decryptedString = decToken.decrypt();
        System.out.println("DEC   : " + decryptedString);
        System.out.println("MOST  : " + decToken.mostSignificant());
        System.out.println("LEAST : " + decToken.leastSignificant());
    }
}
