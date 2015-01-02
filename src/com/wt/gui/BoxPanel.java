package com.wt.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import com.wt.utils.MailMessage;

public class BoxPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<MailMessage> messageList = null;
    private JPanel mainPanel;
    private JScrollPane scrollPane = null;
    
    public BoxPanel() {
        super();
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new BorderLayout());
        
        this.messageList = new ArrayList<MailMessage>();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(mainPanel);
        //scrollPane.setViewportView(mainPanel);
        this.add(BorderLayout.CENTER, scrollPane);
        this.updateMessageUI();
    }
    
    public void updateMessageUI() {
        //TODO: update the UI according to mail list
        int cnt = this.mainPanel.getComponentCount();
        
        this.mainPanel.removeAll();
        for (int i = 0; i <= cnt; i++)
            this.mainPanel.add(new MailItemPanel());
        this.scrollPane.updateUI();
    }
    
    public void updateMessageList(ArrayList<MailMessage> messageList) {
        this.messageList = messageList;
    }
    
    public ArrayList<MailMessage> getMessageList() {
        return this.messageList;
    }
}
