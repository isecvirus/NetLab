package org.virus.netlab;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Random;
import java.util.regex.Pattern;

/**
 *
 * @author virus
 * @version 1.0.0
 */
public class IPv4 {

    private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public boolean is(String ipv4) {
        return PATTERN.matcher(ipv4).matches();
    }

    public String random() {
        Random random = new Random();
        
        int octet1 = random.nextInt(256);
        int octet2 = random.nextInt(256);
        int octet3 = random.nextInt(256);
        int octet4 = random.nextInt(256);
        
        return octet1 + "." + octet2 + "." + octet3 + "." + octet4;
    }
    
    public String[] binary(String ipv4) {
        String[] binaryOctets = new String[4];
        
        if (is(ipv4)) {
            String[] octets = ipv4.split("\\.");
            
            for (int i=0;i<octets.length;i++) {
                int octet = Integer.parseInt(octets[i]);
                
                binaryOctets[i] = new Decimal().toBinary(octet);
            }
            
            return binaryOctets;
        }
        
        binaryOctets[0] = "00000000";
        binaryOctets[1] = "00000000";
        binaryOctets[2] = "00000000";
        binaryOctets[3] = "00000000";
        
        return binaryOctets;
    }

    public long decimal(String ipv4) {
        if (is(ipv4)) {
            String[] parts = ipv4.split("\\.");
            long result = 0;
            for (int i = 0; i < parts.length; i++) {
                result = (result << 8) | Integer.parseInt(parts[i]);
            }
            return result;
        }
        return 0;
    }
}
