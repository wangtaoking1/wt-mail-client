package com.wt.main;

import com.wt.gui.MainFrame;
import com.wt.gui.ServerFrame;
import com.wt.utils.Manager;

public class MailClient {

	public static void main(String[] args) {
	    if (!Manager.isLogin()) {
	        new ServerFrame();
	    }
	    else {
	        new MainFrame();
	    }
	}
}
