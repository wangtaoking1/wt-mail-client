package com.wt.main;

import com.wt.utils.MailMessage;
import com.wt.utils.User;
import com.wt.smtp.SMTPClient;
import org.apache.commons.codec.binary.Base64;

public class MailClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MailMessage message = new MailMessage(new User("wangtaoking1",
            "asdfa"));
        message.setFrom("wangtaoking1@localhost");
        message.setTo("abc@localhost");
        message.setSubject("hello");
        message.setContent("hello world");
        SMTPClient client = new SMTPClient(message);
        client.sendMail();
	}

}
