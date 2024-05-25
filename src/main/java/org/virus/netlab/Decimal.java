package org.virus.netlab;

/**
 *
 * @author virus
 */
public class Decimal {
    private int MIN = 0;
    private int MAX = 255;
    
    public boolean is(Object dec) {
        try {
            if (dec instanceof Integer) {
                return ((Integer)dec >= MIN) && ((Integer)dec <= MAX);
            } else if (dec instanceof String) {
                return (Integer.parseInt((String) dec) >= MIN) && (Integer.parseInt((String) dec) <= MAX);
            }
        } catch (java.lang.NumberFormatException e) {}
        
        return false;
    }
    
    public String toBinary(int dec) {
        String bin = Integer.toBinaryString(dec);

        return String.format("%8s", bin).replace(" ", "0");
    }
}
