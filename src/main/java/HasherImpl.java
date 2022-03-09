import org.apache.commons.codec.binary.Hex;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;


public class HasherImpl implements Hasher{
    @Override
    public String hashPassword(String password) {
        try {
            return getHashedPassword(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean validatePassword(String password, String storedPassword) {
        char[] chars = password.toCharArray();
        int iterations = Integer.parseInt(storedPassword.split(":")[0]);
        String salt = storedPassword.split(":")[1];
        int keyLength = 512;

        char[] charArray = salt.toCharArray();
        byte[] newSalt = null;
        try{
            newSalt = Hex.decodeHex(charArray);
        } catch (Exception e) {
            System.out.println("errorino");
        }

        String hashedPassword;
        try {
            hashedPassword = getHashedPasswordShort(chars, newSalt, iterations, keyLength);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e.getMessage());
            return false;
        }

        boolean isEquals = storedPassword.equals(hashedPassword);


        return isEquals;
    }

    private static String getHashedPasswordShort(char[] chars, byte[] salt, int iterations, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();

        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static String getHashedPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {

        int iterations = 1000;
        int keyLength = 512;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();

        return iterations + ":" + toHex(salt) + ":" + toHex(hash);


    }

    public static byte[] getSalt () throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public static String toHex( byte[] array) throws NoSuchAlgorithmException
    {
        char[] smth = Hex.encodeHex(array);

        try {
            byte[] decodedSmth = Hex.decodeHex(smth);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new String(smth);

    }
    
}

