package com.wt.smtp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;

import com.wt.utils.LoggerFactory;
import com.wt.utils.MailMessage;

/**
 * This is a smtp client to send message to server
 * @author wangtao
 * @time 2014/11/13
 */
public class SMTPClient {
    public static Logger logger = LoggerFactory.getLogger(SMTPClient.class);

    private Socket socket = null;
    private BufferedReader input = null;
    private PrintWriter output = null;
    private String server = null;
    private int port;
    private MailMessage message;

    public SMTPClient() {
        logger.info("Create a smtp client");
    }

    public SMTPClient(MailMessage message) {
        logger.info("Create a smtp client");
        this.setMessage(message);
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    public MailMessage getMessage() {
        return message;
    }
    public void setMessage(MailMessage message) {
        this.message = message;
        this.setServerInfo();
    }

    private void setServerInfo() {
        int pos = this.message.getFrom().indexOf("@");
        server = this.message.getFrom().substring(pos + 1);
        if ("yahoo.com".equals(server) || "gmail.com".equals(server)) {
            port = 465;
        } else
            port = 25;

        boolean flag = Pattern.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$|localhost", 
            server);
        if (flag) {
            port = 465;
        } else {
            server = "smtp." + server;
        }

        logger.debug("server: " + server + "\tport: " + String.valueOf(port));
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
     * Send the message to the server
     * @return whether the message is sent successfully
     */
    public boolean sendMail() {
        boolean flag = true;
        try {
            init();

            sayHelo();

            login();

            setEnvelop();

            sendMessage();

            quit();

            logger.info("Send the message from " + this.message.getFrom() 
                + " to " + this.message.getTo() + " successfully");
        } catch (Exception e) {
            logger.error(e);
            flag = false;
            logger.info("Send the message from " + this.message.getFrom() 
                    + " to " + this.message.getTo() + " failed");
        } finally {
            this.close();
        }
        return flag;
    }

    
    /**
     * Initial the socket, input, output
     * @throws Exception
     */
    public void init() throws Exception {
        logger.debug("Connecting " + server + " ...");

        socket = new Socket(server, port);
        input = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        int token = getResultToken();
        if (token != 220) {
            throw new Exception("Connected " + server + " fail");
        }
        logger.debug("Connected " + server + " successfully");
    }

    
    /**
     * Register from the server
     * @throws IOException
     */
    public void sayHelo() throws Exception {
        this.sendData("HELO " + this.server);
        int token = this.getResultToken();

        if (token != 250) {
            throw new Exception("say helo to the server fail");
        }
        logger.debug("say helo to the server successfully");
    }

    
    /**
     * Auth and login
     * @throws Exception
     */
    public void login() throws Exception {
        this.sendData("AUTH LOGIN");
        int token = this.getResultToken();
        if (token != 334)
            throw new Exception("login fail");
        
        this.sendData(Base64.encodeBase64String(this.message.getUser().
            getUsername().getBytes()));
        token = this.getResultToken();
        if (token != 334)
            throw new Exception("login fail");
        
        this.sendData(Base64.encodeBase64String(this.message.getUser().
            getPassword().getBytes()));
        token = this.getResultToken();
        if (token != 235)
            throw new Exception("login fail");
        
        logger.debug("login successfully");
    }

    
    /**
     * Set the mail from and to
     * @throws Exception
     */
    public void setEnvelop() throws Exception {
        //set the mail from
        this.sendData("MAIL From:<" + this.message.getFrom() + ">");
        int token = this.getResultToken();
        if (token != 250)
            throw new Exception("set envelop fail");

        //set the mail to
        this.sendData("RCPT To:<" + this.message.getTo() + ">");
        token = this.getResultToken();
        if (token != 250)
            throw new Exception("set envelop fail");

        logger.debug("set envelop successfully");
    }

    
    /**
     * Send the content of the mail message
     * @throws Exception
     */
    public void sendMessage() throws Exception {
        this.sendData("Data");
        int token = this.getResultToken();
        if (token != 354)
            throw new Exception("send 'data' command fail");

        logger.debug(this.message.getContent());
        
        this.sendData(this.message.getContent());
        this.sendData(".");
        token = this.getResultToken();
        if (token != 250)
            throw new Exception("send the body of message fail");

        logger.debug("send the message successfully");
    }

    
    /**
     * Quit from the connection with the server
     * @throws Exception
     */
    public void quit() throws Exception {
        this.sendData("QUIT");
        int token = this.getResultToken();
        
        logger.debug(token);
        if (token != 221) {
            throw new Exception("quit fail");
        }
        logger.debug("quit successfully");
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
     * @return "the token of the returned string"
     */
    private int getResultToken() {
        String line = "";
        try {
            line = this.input.readLine();
        } catch (IOException e) {
            logger.error(e);
        }

        logger.debug(line);

        StringTokenizer get = new StringTokenizer(line, " ");
        return Integer.parseInt(get.nextToken());
    }
}
