package com.wt.utils;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class ManagerTest {

    @Test
    public void testUserExists() {
        Assert.assertTrue(Manager.userExists("test1"));
    }
    
    
    @Test
    public void testAuth() {
        Assert.assertTrue(Manager.auth("test1", "test1"));
    }
    
    
    @Test
    public void testRegUser() {
        Assert.assertTrue(Manager.regUser("test1", "test1"));
    }
    
    
    @Test
    public void testUnregUser() {
        Assert.assertTrue(Manager.unregUser("test1", "test1"));
    }
}
