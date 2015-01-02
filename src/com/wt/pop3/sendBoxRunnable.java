package com.wt.pop3;

import org.apache.log4j.Logger;

import com.wt.gui.BoxPanel;
import com.wt.utils.LoggerFactory;

public class sendBoxRunnable implements Runnable {
    private Logger logger = LoggerFactory.getLogger(sendBoxRunnable.class);

    private BoxPanel boxPanel = null;
    
    public sendBoxRunnable(BoxPanel boxPanel) {
        this.boxPanel = boxPanel;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            
            
            try {
                Thread.sleep(3 *1000);
            }
            catch (Exception e) {
                break;
            }
        }
        
        new Thread(new sendBoxRunnable(this.boxPanel)).start();
    }
    
    private boolean checkUpdate() {
        return false;
    }

}
