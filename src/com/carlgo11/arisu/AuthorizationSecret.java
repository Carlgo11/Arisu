package gauth;

import java.util.Arrays;
import java.util.Random;
import org.apache.commons.codec.binary.Base32;

public class AuthorizationSecret {

    public static String getSecret() {
       // Allocating the buffer
        byte[] buffer
                = new byte[80 / 8 + 5 * 4];

        // Filling the buffer with random numbers.
        // Notice: you want to reuse the same random generator
        // while generating larger random number sequences.
        new Random().nextBytes(buffer);
        Base32 codec = new Base32();
        byte[] secretKey = Arrays.copyOf(buffer, 10);
        byte[] bEncodedKey = codec.encode(secretKey);
        String encodedKey = new String(bEncodedKey);
        String format = getQRBarcodeURL(encodedKey);
        return encodedKey;
    }

    public static String getQRBarcodeURL(
            String secret) {
        String host = "Carlgo11";
        String user = "Arisu";
        String format = "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s%3Fsecret%%3D%s";
        return String.format(format, user, secret);
    }

}
