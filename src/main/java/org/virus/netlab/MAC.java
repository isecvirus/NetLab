/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.virus.netlab;

import java.util.Random;
import java.util.regex.Pattern;

/**
 *
 * @author virus
 */
public class MAC {
    private static final Pattern PATTERN = Pattern.compile("^([0-9a-fA-F][0-9a-fA-F]:){5}([0-9a-fA-F][0-9a-fA-F])$");
    
    public boolean is(String mac) {
        return PATTERN.matcher(mac).matches();
    }
    
    public String random() {
        Random random = new Random();
        
        // Generate the six octets of the MAC address
        int[] octets = new int[6];
        for (int i = 0; i < octets.length; i++) {
            octets[i] = random.nextInt(256);
        }
        
        // Format the MAC address as a string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < octets.length; i++) {
            sb.append(String.format("%02X", octets[i]));
            if (i < octets.length - 1) {
                sb.append(":");
            }
        }
        
        return sb.toString();
    }
}
