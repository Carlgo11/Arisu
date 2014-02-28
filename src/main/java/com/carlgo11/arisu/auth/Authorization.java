package com.carlgo11.arisu.auth;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base32;

public class Authorization {
    /*
     * This class is authorizing with google authenticator.
     * This class should not be publichsed because of security reasons.
     */
public static boolean check_code(
  String secret,
  long code,
  long t)
    throws NoSuchAlgorithmException,
      InvalidKeyException {
    boolean works = false;
  Base32 codec = new Base32();
  byte[] decodedKey = codec.decode(secret);

  System.out.println("decodedKey: "+decodedKey.toString());
  System.out.println("secret: "+secret);
  // Window is used to check codes generated in the near past.
  // You can use this value to tune how far you're willing to go. 
  int window = 2;
  for (int i = -window; i <= window; ++i) {
    long hash = verify_code(decodedKey, t + i);

System.out.println("hash: "+hash+" code: "+code);
    if (hash == code) {
        System.out.println("Match!");
      works=true;
    }else{
        System.out.println("No match!");
    }
    
  }
  
  return works;
}





public static int verify_code(
  byte[] key,
  long t)
  throws NoSuchAlgorithmException,
    InvalidKeyException {
  byte[] data = new byte[8];
  long value = t;
  for (int i = 8; i-- > 0; value >>>= 8) {
    data[i] = (byte) value;
  }


  SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
  Mac mac = Mac.getInstance("HmacSHA1");
  mac.init(signKey);
  byte[] hash = mac.doFinal(data);


  int offset = hash[20 - 1] & 0xF;
  
  // We're using a long because Java hasn't got unsigned int.
  long truncatedHash = 0;
  for (int i = 0; i < 4; ++i) {
    truncatedHash <<= 8;
    // We are dealing with signed bytes:
    // we just keep the first byte.
    truncatedHash |= (hash[offset + i] & 0xFF);
  }


  truncatedHash &= 0x7FFFFFFF;
  truncatedHash %= 1000000;


  return (int) truncatedHash;
}
}
