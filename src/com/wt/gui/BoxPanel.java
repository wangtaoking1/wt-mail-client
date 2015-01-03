package com.wt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
    
    
    /**
     * To update the receive box UI according to the mail list
     */
    public void updateMessageUI() {
        int cnt = this.messageList.size();
        this.mainPanel.removeAll();
        for (int i = 0; i < cnt; i++) {
            MailItemPanel item = new MailItemPanel(BoxPanel.this);
            MailMessage message = this.messageList.get(i);
            item.updateMails(message.getFrom(), message.getTime(), 
                    message.getSubject());
            this.mainPanel.add(item);
        }
        this.removeAllFocus();
        this.scrollPane.updateUI();
    }
    
    public void updateMessageList(ArrayList<MailMessage> messageList) {
        this.messageList = messageList;
    }
    
    public ArrayList<MailMessage> getMessageList() {
        return this.messageList;
    }
    
    public void removeAllFocus() {
        for (Component comp : this.mainPanel.getComponents()) {
            comp.setBackground(Color.WHITE);
        }
    }
    
    public Component[] getAllMailItems() {
        return this.mainPanel.getComponents();
    }
    
    public MailMessage getMessage(int n) {
        return this.messageList.get(n);
    }
}
