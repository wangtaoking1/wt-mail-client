package com.wt.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        super();
        this.setTitle("WT Mail Client");
        
        this.setSize(800, 600);
        this.setVisible(true);
    }
    
    public boolean isLogin() {
        return false;
    }
}
