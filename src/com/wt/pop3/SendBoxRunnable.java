package com.wt.pop3;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.wt.gui.BoxPanel;
import com.wt.manage.Manager;
import com.wt.utils.LoggerFactory;
import com.wt.utils.MailMessage;

/**
 * This is a thread to update send box
 * @author wangtao
 * @time 2014/12/25
 */
public class SendBoxRunnable implements Runnable {
    private Logger logger = LoggerFactory.getLogger(SendBoxRunnable.class);

    private BoxPanel boxPanel = null;
    public Runnable runx;
    
    public SendBoxRunnable(BoxPanel boxPanel) {
        this.boxPanel = boxPanel;
        
        this.boxPanel = boxPanel;
        
        runx = new Runnable() {
            public void run() {
                SendBoxRunnable.this.boxPanel.updateMessageUI();
            }
        };
    }
    
    @Override
    public void run() {
        logger.info("a new sendBoxThread started");
        
        while (true) {
            try {
                if (!Manager.isLogin())
                    break;
                
                if (this.checkUpdate()) {
                    this.boxPanel.updateMessageList(this.getMessageList());
                }
                
                //update UI
                SwingUtilities.invokeAndWait(runx);
                
                Thread.sleep(5 * 1000);
            }
            catch (Exception e) {
                logger.error(e);
                break;
            }
        }
        
        logger.info("sendBoxThread dead");
    }
    
    
    /**
     * To check whether or not need to update mails in send box
     * @return true for need to update, false for not
     */
    private boolean checkUpdate() {
        POPClient client = new POPClient();
        
        int cnt = client.getSendMailsCount();
        if (cnt == boxPanel.getMessageList().size()) {
            return false;
        }
        return true;
    }

    
    /**
     * To get the mail list from server
     * @return
     */
    private ArrayList<MailMessage> getMessageList() {
        ArrayList<MailMessage> mailList = new ArrayList<MailMessage>();
        POPClient client = new POPClient();
        mailList = client.getSendMails();
        
        return mailList;
    }

}
