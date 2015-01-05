package com.wt.pop3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;

import com.wt.manage.Manager;
import com.wt.utils.ConfigParser;
import com.wt.utils.LoggerFactory;
import com.wt.utils.MailMessage;


/**
 * This is a pop client to pull message from server
 * @author wangtao
 * @time 2014/12/25
 */
public class POPClient {
    private Logger logger = LoggerFactory.getLogger(POPClient.class);

    private Socket socket = null;
    private BufferedReader input = null;
    private PrintWriter output = null;
    private String server = null;
    private int port;

    public POPClient() {
//        ConfigParser parser = new ConfigParser("wt_mail.properties");
//        this.setServerInfo(parser.getOption("pop_server"), 
//                Integer.parseInt(parser.getOption("pop_port")));
//        parser.closeFile();
        
        this.setServerInfo(Manager.server, 110);
        
        try {
            this.init();
        }
        catch (Exception e) {
            logger.error("pop client init failed");
            this.close();
            return;
        }
        
        if (!this.auth(Manager.username, Manager.password)) {
            logger.info(Manager.username + " auth failed");
            return ;
        }
        logger.debug("Create a pop client");
    }

    
    public void close() {
        try {
            this.sendData("quit");
            String line = this.input.readLine();
        }
        catch (Exception e) {
            logger.error(e);
        }
        
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    
    /**
     * Set server information
     * @param server
     * @param port
     */
    public void setServerInfo(String server, int port) {
        this.server = server;
        this.port = port;
    }

    
    /**
     * Initial the socket, input, output
     * @throws Exception
     */
    private void init() throws Exception {
        logger.debug("Connecting " + server + " ...");

        socket = new Socket(server, port);
        input = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        String token = getResultToken();
        if (!token.equalsIgnoreCase("+OK")) {
            throw new Exception("Connected " + server + " fail");
        }
        logger.debug("Connected " + server + " successfully");
    }


    /**
     * To auth user from the pop server
     * @param user
     * @param pass
     * @return
     */
    private boolean auth(String user, String pass) {
        try {
            this.sendData("user " + user);
            String token = this.getResultToken();
            if (!token.equalsIgnoreCase("+OK"))
            {
                logger.info("User " + user + " login failed");
                return false;
            }
            
            this.sendData("pass " + pass);
            token = this.getResultToken();
            if (!token.equalsIgnoreCase("+OK"))
            {
                logger.info("User " + user + " login failed");
                return false;
            }
        }
        catch (Exception e) {
            logger.error(e);
            return false;
        }
        return true;
    }
    
    
    /**
     * Send message to server
     * @param data "the data needed to send to the server"
     * @return "the returned token from the server"
     */
    private void sendData(String data) throws IOException {
        this.output.println(data);
        this.output.flush();

        logger.debug("Send '" + data + "' to the server successfully");
    }

    
    /**
     *
     * @return "the return code of the returned string"
     */
    private String getResultToken() {
        String line = "";
        try {
            line = this.input.readLine();
        } catch (IOException e) {
            logger.error(e);
        }

        logger.debug(line);

        String[] items = line.split(" ");
        return items[0];
    }
    
    
    /**
     * To get the receive mails of user
     * @return
     */
    public ArrayList<MailMessage> getReceiveMails() {
        ArrayList<MailMessage> mailList = new ArrayList<MailMessage>();
        int cnt = 0;
        //get the number of mails
        try {
            this.sendData("stat");
            String line = this.input.readLine();
            String[] items = line.split(" ");
            if (items[0].equalsIgnoreCase("+OK")) {
                cnt = Integer.parseInt(items[1]);
            }
        }
        catch (Exception e) {
            logger.error(e);
        }
        
        //get the info of every mail
        for (int i = 1; i <= cnt; i++) {
            mailList.add(this.getReceiveMailInfo(i));
        }
        
        this.close();
        return mailList;
    }
    
    
    /**
     * To get the receive mail count of user
     * @return
     */
    public int getReceiveMailsCount() {
        int cnt = 0;
        try {
            this.sendData("stat");
            String line = this.input.readLine();
            String[] items = line.split(" ");
            if (items[0].equalsIgnoreCase("+OK")) {
                cnt = Integer.parseInt(items[1]);
            }
        }
        catch (Exception e) {
            logger.error(e);
        }
        
        this.close();
        return cnt;
    }
    
    
    /**
     * To get the number n mail of user
     * @param n
     * @return
     */
    private MailMessage getReceiveMailInfo(int n) {
        MailMessage message = new MailMessage();
        String content = null;
        try {
            this.sendData("retr " + n);
            StringBuffer buffer = new StringBuffer();
            while (true) {
                String line = this.input.readLine();
                if (line.equals("."))
                    break;
                buffer.append(line + "\n");
            }
            
            content = buffer.toString();
        }
        catch (Exception e) {
            logger.error(e);
        }
        
        String[] content_items = content.split("\n\n", 2);
        String[] headers = content_items[0].split("\n");
        for (String header : headers) {
            String[] items = header.split(": ", 2);
            switch (items[0].toLowerCase()) {
            case "time":
                message.setTime(items[1]);
                break;
            case "from":
                message.setFrom(items[1]);
                break;
            case "to":
                message.setTo(items[1]);
                break;
            case "subject":
                message.setSubject(items[1]);
                break;
            }
        }
        message.setContent(content_items[1]);
        
        boolean flag = false;
        try {
            this.sendData("isr " + n);
            String line = this.input.readLine();
            if (line.equals("1"))
                flag = true;
        }
        catch (Exception e) {
            logger.error(e);
        }
        message.setReaded(flag);
        
        return message;
    }
    

    /**
     * To del the number index mail of user received
     * @param index
     * @return
     */
    public boolean delReceiveMail(int index) {
        boolean flag = false;
        try {
            this.sendData("dele " + index);
            flag = true;
        }
        catch (Exception e) {
            logger.error(e);
            flag = false;
        }
        
        this.close();
        return flag;
    }
    
    
    /**
     * To get the send mail count of user
     * @return
     */
    public int getSendMailsCount() {
        int cnt = 0;
        try {
            this.sendData("sstat");
            String line = this.input.readLine();
            String[] items = line.split(" ");
            if (items[0].equalsIgnoreCase("+OK")) {
                cnt = Integer.parseInt(items[1]);
            }
        }
        catch (Exception e) {
            logger.error(e);
        }
        
        this.close();
        return cnt;
    }
    
    
    /**
     * To get the number n mail of user sended
     * @param n
     * @return
     */
    private MailMessage getSendMailInfo(int n) {
        MailMessage message = new MailMessage();
        String content = null;
        try {
            this.sendData("sretr " + n);
            StringBuffer buffer = new StringBuffer();
            while (true) {
                String line = this.input.readLine();
                if (line.equals("."))
                    break;
                buffer.append(line + "\n");
            }
            
            content = buffer.toString();
        }
        catch (Exception e) {
            logger.error(e);
        }
        
        String[] content_items = content.split("\n\n", 2);
        String[] headers = content_items[0].split("\n");
        for (String header : headers) {
            String[] items = header.split(": ", 2);
            switch (items[0].toLowerCase()) {
            case "time":
                message.setTime(items[1]);
                break;
            case "from":
                message.setFrom(items[1]);
                break;
            case "to":
                message.setTo(items[1]);
                break;
            case "subject":
                message.setSubject(items[1]);
                break;
            }
        }
        message.setContent(content_items[1]);
        
        message.setReaded(true);
        
        return message;
    }
    
    
    /**
     * To get the receive mails of user
     * @return
     */
    public ArrayList<MailMessage> getSendMails() {
        ArrayList<MailMessage> mailList = new ArrayList<MailMessage>();
        int cnt = 0;
        //get the number of mails
        try {
            this.sendData("sstat");
            String line = this.input.readLine();
            String[] items = line.split(" ");
            if (items[0].equalsIgnoreCase("+OK")) {
                cnt = Integer.parseInt(items[1]);
            }
        }
        catch (Exception e) {
            logger.error(e);
        }
        
        //get the info of every mail
        for (int i = 1; i <= cnt; i++) {
            mailList.add(this.getSendMailInfo(i));
        }
        
        this.close();
        return mailList;
    }
    
    
    /**
     * To del the number index mail of user sended
     * @param index
     * @return
     */
    public boolean delSendMail(int index) {
        boolean flag = false;
        try {
            this.sendData("sdele " + index);
            flag = true;
        }
        catch (Exception e) {
            logger.error(e);
            flag = false;
        }
        
        this.close();
        return flag;
    }
    
    
    /**
     * To read the mail
     * @param index
     * @return
     */
    public boolean readMail(int index) {
        boolean flag = false;
        
        try {
            this.sendData("read " + index);
            String line = this.input.readLine();
            if (line.equalsIgnoreCase("+OK"))
                flag = true;
        }
        catch (Exception e) {
            logger.error(e);
            flag = false;
        }
        
        this.close();
        return flag;
    }
}
