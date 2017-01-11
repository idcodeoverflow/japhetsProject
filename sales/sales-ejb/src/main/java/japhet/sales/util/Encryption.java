package japhet.sales.util;

import java.security.MessageDigest;

public class Encryption {
	
	private static String SHA256 = "SHA-256";

	public static String SHA256(String str) throws Exception{
		MessageDigest message = MessageDigest.getInstance(SHA256);
		byte[] SHA256_VALUE1 = message.digest(str.getBytes());
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < SHA256_VALUE1.length ; i++){
            sb.append(Integer.toString((SHA256_VALUE1[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
	}
	
	
}
