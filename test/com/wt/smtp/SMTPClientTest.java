package com.wt.smtp;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.wt.utils.MailMessage;
import com.wt.utils.User;

public class SMTPClientTest {

	@Test
	public void testSendMail() {
	    MailMessage message = new MailMessage(new User("test0",
	            "test0"));
	    message.setFrom("test0@10.0.2.4");
	    message.setTo("test0@10.0.2.4");
	    message.setContent("from: test0@10.0.2.4\nto: test0@10.0.2.4\n" + 
	            "subject: hello\n\nI am so happy!!");
	    SMTPClient client = new SMTPClient(message);
	    client.sendMail();
		
	}
}
