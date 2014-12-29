package com.wt.smtp;

import com.wt.utils.MailMessage;

public class SMTPRunnable implements Runnable {

    private MailMessage message = null;
    
    public SMTPRunnable(MailMessage message) {
        this.message = message;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        SMTPClient client = new SMTPClient(this.message);
        client.sendMail();
        
        SMTPClient.logger.info("Send mail from " + client.getMessage()
                .getFrom() + " to " + client.getMessage().getTo());
    }

}
