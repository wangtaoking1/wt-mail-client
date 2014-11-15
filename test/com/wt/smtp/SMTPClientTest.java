package com.wt.smtp;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.wt.utils.MailMessage;
import com.wt.utils.User;

public class SMTPClientTest {

	@Test
	public void testSendMail() {
		SMTPClient client = new SMTPClient();
		User user = new User("wangtaoking1", "xxxxxx");
		
		MailMessage message = new MailMessage(user);
		message.setFrom("wangtaoking1@163.com");
		message.setTo("479021795@qq.com");
		message.setSubject("hello");
		message.setContent("hello world!!");
		client.setMessage(message);
		
		Assert.assertTrue(client.sendMail());
		
	}

}
