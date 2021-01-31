package com.charakhovich.club.model.util;


import org.testng.annotations.Test;

@Test
public class EncryptorTest {
    public void test() {
        String result=Encryptor.hashPassword("1Alexey33");
        System.out.println();
    }

}