package org.virus.netlab;

/**
 *
 * @author virus
 */
public class CIDR {
    public String ip(int cidr) {
        /**
         *
         * <In 16
         * Out> 255.255.0.0
         *
         * @param cidr
         */
        
        int maxCIDR = 32;
        int mask = 0xFFFFFFFF << (maxCIDR - cidr);
        
        StringBuilder sb = new StringBuilder();
        for (int c = 3; c >= 0; c--) {
            int shift = c * 8;
            int octet = (mask >> shift) & 0xFF;
            sb.append(octet);
            if (c > 0) {
                sb.append(".");
            }
        }

        return sb.toString();
    }
}
