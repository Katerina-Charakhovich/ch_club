package com.charakhovich.club.web.command.factory;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ActionFactoryTest {

    @Test
    public void testDefineCommand() {
        //ActionFactory.defineCommand("/do/change_locale");
    }

    @Test
    public void testSubStringPathWithRegex() {
        String str =ActionFactory.subStringPathWithRegex("/do/change_locale");
        System.out.println();
    }

}