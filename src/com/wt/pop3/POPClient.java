package com.wt.pop3;

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

    
    public POPClient(String server, int port) {
        this.setServerInfo(server, port);
        logger.info("Create a pop client");
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
    public void init() throws Exception {
        logger.info("Connecting " + server + " ...");

        socket = new Socket(server, port);
        input = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        String token = getResultToken();
        if (!token.equalsIgnoreCase("+OK")) {
            throw new Exception("Connected " + server + " fail");
        }
        logger.info("Connected " + server + " successfully");
    }


    /**
     * To auth user from the pop server
     * @param user
     * @param pass
     * @return
     */
    public boolean auth(String user, String pass) {
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
}
