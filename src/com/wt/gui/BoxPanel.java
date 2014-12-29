package com.wt.gui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import com.wt.utils.MailMessage;

public class BoxPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<MailMessage> messageList = null;
    private JScrollPane scrollPane = null;
    
    public BoxPanel() {
        super();
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.messageList = new ArrayList<MailMessage>();
        scrollPane = new JScrollPane();
        
        this.updateMessageUI();
    }
    
    public void updateMessageUI() {
        
    }
    
    public void updateMessageList() {
        
    }
}
