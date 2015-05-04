package sit.khaycake.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class Encryption {
    public static String md5(String input) {
        try {
            byte[] bytesOfMessage = input.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytesOfMessage);
            return new BigInteger(1, digest).toString(16);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            return null;
        }
    }
}
