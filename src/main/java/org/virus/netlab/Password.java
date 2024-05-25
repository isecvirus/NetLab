/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.virus.netlab;

/**
 *
 * @author virus
 */
public class Password {

    public String generate(int len) {
        String password = "";
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+~`-=[]{}\\|;:'\"<,>.?/";

        try {
            for (int i = 0; i < len; i++) {
                int random_index = (int) (Math.random() * characters.length());
                password += characters.charAt(random_index);
            }
        } catch (Exception error) {
        }
        
        return password;
    }
}
