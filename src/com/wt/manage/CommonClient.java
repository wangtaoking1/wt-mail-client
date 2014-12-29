package com.wt.manage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.wt.utils.LoggerFactory;
import com.wt.utils.Manager;

public class CommonClient {
    private Logger logger = LoggerFactory.getLogger(CommonClient.class);
    
    private Socket socket = null;
    private BufferedReader input = null;
    private PrintWriter output = null;
    private String server = null;
    private int port;
    
    public CommonClient(String server) {
        this.server = server;
        this.port = 5055;
        
        //Initial socket, input and output stream
        try {
            socket = new Socket(this.server, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (Exception e) {
            logger.error(e);
        }
        
        logger.info("Common client connects server successfully");
    }
    
    
    /**
     * To send data to the server and get the return data
     * @param message
     * @return
     */
    public String sendMessage(String message) {
        logger.debug("send: " + message);
        this.output.println(message);
        this.output.flush();
        
        try {
            String ret = this.input.readLine();
            
            logger.debug("return: " + ret);
            return ret;
        }
        catch (Exception e) {
            return "";
        }
    }
    
    /**
     * To close the socket
     */
    public void close() {
        if (socket != null) {
            try {
                socket.close();
            }
            catch (Exception e) {
                logger.error(e);
            }
            socket = null;
        }
    }
}
 