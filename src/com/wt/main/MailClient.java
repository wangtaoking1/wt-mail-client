package com.wt.main;

import com.wt.gui.MainFrame;
import com.wt.gui.ServerFrame;
import com.wt.manage.Manager;

public class MailClient {

	public static void main(String[] args) {
	    if (!Manager.isLogin()) {
	        //User has not login
	        new ServerFrame();
	    }
	    else {
	        //User has login
	        new MainFrame();
	    }
	}
}
