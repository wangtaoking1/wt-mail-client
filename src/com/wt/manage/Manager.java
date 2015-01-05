package com.wt.manage;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.wt.utils.ConfigParser;


/**
 * Manager is to manage the user info and communicate with the manage service
 * @author wangtao
 * @time 2014/12/26
 */
public class Manager {
    public static String server = null;
    public static String username = null;
    public static String password = null;
    private static boolean isLogin = false;
    
    
    /**
     * To read user data from cookies
     */
    public static void readData() {
        ConfigParser parser = new ConfigParser("wt_mail.properties");
        String dataPath = parser.getOption("data_path");
        parser.closeFile();
        
        parser = new ConfigParser(dataPath);
        if (!parser.getOption("server").equalsIgnoreCase("null"))
            Manager.server = parser.getOption("server");
        if (!parser.getOption("username").equalsIgnoreCase("null"))
            Manager.username = parser.getOption("username");
        if (!parser.getOption("password").equalsIgnoreCase("null"))
            Manager.password = parser.getOption("password");
        if (parser.getOption("isLogin").equalsIgnoreCase("true"))
            Manager.isLogin = true;
        else
            Manager.isLogin = false;
        parser.closeFile();
    }
    
    
    /**
     * To write user data into cookies
     */
    public static void writeData() {
        ConfigParser parser = new ConfigParser("wt_mail.properties");
        String dataPath = "data/cookies";
        if (parser.getOption("data_path") != null)
            dataPath = parser.getOption("data_path");
        parser.closeFile();
        
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileOutputStream(dataPath), true);
            output.println("server = " + Manager.server);
            output.println("username = " + Manager.username);
            output.println("password = " + Manager.password);
            output.println("isLogin = " + Manager.isLogin);
            output.flush();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        if (output != null) {
            output.close();
            output = null;
        }
        
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
