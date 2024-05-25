package org.virus.netlab;

/**
 *
 * @author virus
 */
public class Binary {
    public boolean is(String bin) {
        return bin.matches("^[01]{1,8}$");
    }
    
    public int toDecimal(String bin) {
        if (is(bin)) {
            int out = 0;
            int binInt = Integer.parseInt(bin);

            for (int i=0;i<bin.length();i++) {
                int digit = binInt % 10;
                out += digit * Math.pow(2, i);
                binInt /= 10;
            }

            return out;
        }
        
        return 0;
    }
}
