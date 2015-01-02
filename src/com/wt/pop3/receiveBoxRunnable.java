package com.wt.pop3;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.wt.gui.BoxPanel;
import com.wt.utils.LoggerFactory;
import com.wt.utils.MailMessage;
import com.wt.utils.Manager;


public class receiveBoxRunnable implements Runnable {
    private Logger logger = LoggerFactory.getLogger(sendBoxRunnable.class);

    private BoxPanel boxPanel = null;
    public Runnable runx;
    
    public receiveBoxRunnable(BoxPanel boxPanel) {
        this.boxPanel = boxPanel;
        
        runx = new Runnable() {
            public void run() {
                receiveBoxRunnable.this.boxPanel.updateMessageUI();
            }
        };
    }
    
    @Override
    public void run() {
        logger.info("a new receiveBoxThread started");
        
        while (true) {
            try {
                if (this.checkUpdate()) {
                    this.boxPanel.updateMessageList(this.getMessageList());
                    
                    //update UI
                    SwingUtilities.invokeAndWait(runx);
                }

                Thread.sleep(1 * 1000);
            }
            catch (Exception e) {
                logger.error(e);
                break;
            }
        }
        
        logger.info("old receiveBoxThread dead");
        new Thread(new sendBoxRunnable(this.boxPanel)).start();
    }
    
    
    /**
     * To check whether or not need to update mails
     * @return true for need to update, false for not
     */
    private boolean checkUpdate() {
        POPClient client = new POPClient();
        if (client.auth(Manager.username, Manager.password)) {
            int cnt = client.getMailCount();
            if (cnt == boxPanel.getMessageList().size()) {
                return false;
            }
            return true;
        }
        return false;
    }

    
    /**
     * To get the mail list from server
     * @return
     */
    private ArrayList<MailMessage> getMessageList() {
        ArrayList<MailMessage> mailList = new ArrayList<MailMessage>();
        POPClient client = new POPClient();
        if (client.auth(Manager.username, Manager.password)) {
            mailList = client.getReceiveMails();
        }
        
        return mailList;
    }
}
