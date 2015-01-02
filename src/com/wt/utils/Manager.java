package com.wt.utils;

import com.wt.manage.CommonClient;

/**
 * 
 * @author wangtao
 * @time 2014/12/26
 */
public class Manager {
    public static String server = null;
    public static String username = null;
    public static String password = null;
    private static boolean isLogin = false;
    
    public static void readData() {
        Manager.server = "10.0.2.4";
        Manager.username = "test0";
        Manager.password = "test0";
        Manager.isLogin = true;
    }
    
    public static void writeData() {
        
    }
    
    
    /**
     * To check whether this user is local user
     * @param username
     * @return
     */
    public static boolean userExists(String username) {
        CommonClient client = new CommonClient();
        String ret = client.sendMessage("has " + username);
        client.close();
        if (ret.equalsIgnoreCase("true"))
            return true;
        return false;
    }
    
    
    /**
     * To auth this user from the server
     * @param username
     * @param password
     * @return
     */
    public static boolean auth(String username, String password) {
        CommonClient client = new CommonClient();
        String ret = client.sendMessage("auth " + username + " " + password);
        client.close();
        
        if (ret.equalsIgnoreCase("true")) {
            Manager.isLogin = true;
            return true;
        }
        return false;
    }
    
    
    /**
     * To logout
     * @return
     */
    public static boolean logout() {
        Manager.username = null;
        Manager.password = null;
        Manager.server = null;
        Manager.isLogin = false;
        return true;
    }
    
    
    /**
     * To check whether the user is login
     * @return
     */
    public static boolean isLogin() {
        Manager.readData();
        return Manager.isLogin;
    }
    
    
    /**
     * To register the user
     * @return
     */
    public static boolean regUser(String username, String password) {
        CommonClient client = new CommonClient();
        String ret = client.sendMessage("reg " + username + " " + password);
        
        if (ret.equalsIgnoreCase("+OK"))
            return true;
        return false;
    }
    
    
    /**
     * To unregister the user
     * @param username
     * @param password
     * @return
     */
    public static boolean unregUser(String username, String password) {
        CommonClient client = new CommonClient();
        String ret = client.sendMessage("unreg " + username + " " + password);
        
        if (ret.equalsIgnoreCase("+OK"))
            return true;
        return false;
    }
}
