package com.wt.smtp;

import com.wt.utils.MailMessage;


/**
 * This is a Thread to send mail
 * @author wangtao
 * @time 2014/12/26
 */
public class SMTPRunnable implements Runnable {

    private MailMessage message = null;
    
    public SMTPRunnable(MailMessage message) {
        this.message = message;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        SMTPClient client = new SMTPClient(this.message);
        boolean flag = client.sendMail();
        
        if (flag)
            SMTPClient.logger.info("Send mail from " + client.getMessage()
                    .getFrom() + " to " + client.getMessage().getTo() + 
                    " successed");
        else 
            SMTPClient.logger.info("Send mail from " + client.getMessage()
                    .getFrom() + " to " + client.getMessage().getTo() + 
                    " failed");
    }

}
