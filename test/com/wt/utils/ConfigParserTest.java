package com.wt.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigParserTest {

    @Test
    public void testGetOption() {
        ConfigParser parser = new ConfigParser("wt_mail.properties");
        System.out.println("pop server: " + parser.getOption("pop_server"));
        System.out.println("pop port: " + parser.getOption("pop_port"));
        
        parser.closeFile();
    }

}
