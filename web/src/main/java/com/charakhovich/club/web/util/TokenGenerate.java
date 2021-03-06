package com.charakhovich.club.web.util;

import java.util.Random;
/**
 * The type Pagination tag.
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class TokenGenerate {
    public static String generateString(int length)
    {
        String characters = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOASDFGHJKLZXCVBNM";
        Random rnd = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rnd.nextInt(characters.length()));
        }
        return new String(text);
    }
}
