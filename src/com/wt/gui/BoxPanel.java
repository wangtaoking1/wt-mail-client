package com.wt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import com.wt.pop3.POPClient;
import com.wt.utils.MailMessage;

/**
 * BoxPanel is the mail box Panel
 * @author wangtao
 * @time 2014/12/25
 */
public class BoxPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<MailMessage> messageList = null;
    
    public static enum BoxType {RECEIVEBOX, SENDBOX};
    
    private BoxType boxType;
    private MainFrame parent;
    private JPanel funcPanel, mainPanel;
    private JScrollPane scrollPane;
    private JButton delBut, retBut, transBut;
    
    public BoxPanel(MainFrame frame, BoxType type) {
        super();
        this.boxType = type;
        this.parent = frame;
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new BorderLayout());
        
        this.messageList = new ArrayList<MailMessage>();
        
        funcPanel = new JPanel();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(mainPanel);
        
        delBut = new JButton("删除");
        retBut = new JButton("回复");
        transBut = new JButton("转发");
        funcPanel.add(delBut);
        funcPanel.add(retBut);
        funcPanel.add(transBut);
        
        this.add(BorderLayout.SOUTH, funcPanel);
        this.add(BorderLayout.CENTER, scrollPane);
        //this.updateMessageUI();
        
        this.addActionListeners();
    }
    
    
    /**
     * To update the receive box UI according to the mail list
     */
    public void updateMessageUI() {
        int cnt = this.messageList.size();
        this.mainPanel.removeAll();
        
        int unreaded = 0;
        for (int i = 0; i < cnt; i++) {
            MailItemPanel item = new MailItemPanel(BoxPanel.this);
            MailMessage message = this.messageList.get(i);
            
            if (this.boxType == BoxType.RECEIVEBOX) {
                item.updateMails(message.getFrom(), message.getTime(), 
                        message.getSubject());
            }
            else {
                item.updateMails(message.getTo(), message.getTime(), 
                        message.getSubject());
            }
            
            //Update the unread mail UI
            if (message.getReaded()) {
                item.setHasReadUI();
            }
            else {
                item.setUnReadUI();
                unreaded++;
            }
            
            this.mainPanel.add(item);
        }
        this.removeAllFocus();
        this.scrollPane.updateUI();

        if (this.boxType == BoxType.RECEIVEBOX) {
            BoxPanel.this.parent.setReceiveBoxUI(unreaded);
        }
    }
    
    
    /**
     * To update the message list
     * @param messageList
     */
    public void updateMessageList(ArrayList<MailMessage> messageList) {
        this.messageList = messageList;
    }
    
    
    /**
     * To get the message list
     * @return
     */
    public ArrayList<MailMessage> getMessageList() {
        return this.messageList;
    }
    
    
    /**
     * To remove all focuses on mail item
     */
    public void removeAllFocus() {
        for (Component comp : this.mainPanel.getComponents()) {
            comp.setBackground(Color.WHITE);
        }
    }
    
    
    /**
     * To get all mail items
     * @return
     */
    public Component[] getAllMailItems() {
        return this.mainPanel.getComponents();
    }
    
    
    /**
     * To get the number n message
     * @param n
     * @return
     */
    public MailMessage getMessage(int n) {
        return this.messageList.get(n);
    }
    
    
    /**
     * To add listeners to some components
     */
    public void addActionListeners() {
        delBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Component[] comps = BoxPanel.this.getAllMailItems();
                int cnt = 0;
                for (cnt = 0; cnt < comps.length; cnt++) {
                    if (comps[cnt].getBackground() == Color.BLACK)
                        break;
                }
                if (cnt != comps.length) {
                    BoxPanel.this.mainPanel.remove(comps[cnt]);
                    BoxPanel.this.messageList.remove(cnt);
                    POPClient client = new POPClient();
                    if (BoxPanel.this.boxType == BoxType.RECEIVEBOX) {
                        client.delReceiveMail(cnt + 1);
                    }
                    else {
                        client.delSendMail(cnt + 1);
                    }
                    BoxPanel.this.updateMessageUI();
                }
                
            }
            
        });
        
        retBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Component[] comps = BoxPanel.this.getAllMailItems();
                int cnt = 0;
                for (cnt = 0; cnt < comps.length; cnt++) {
                    if (comps[cnt].getBackground() == Color.BLACK)
                        break;
                }
                if (cnt != comps.length) {
                    MailMessage message = BoxPanel.this.getMessage(cnt);
                    SendMailPanel mailPanel = BoxPanel.this.parent
                            .getMailPanel();
                    mailPanel.setReceiver(message.getFrom());
                    mailPanel.setSubject("Reply: " + message.getSubject());
                    mailPanel.setContent(BoxPanel.this.createReturnContent(
                            message));
                    BoxPanel.this.parent.changeMainPanel("mail");
                }
            }
            
        });
        
        transBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Component[] comps = BoxPanel.this.getAllMailItems();
                int cnt = 0;
                for (cnt = 0; cnt < comps.length; cnt++) {
                    if (comps[cnt].getBackground() == Color.BLACK)
                        break;
                }
                if (cnt != comps.length) {
                    MailMessage message = BoxPanel.this.getMessage(cnt);
                    SendMailPanel mailPanel = BoxPanel.this.parent
                            .getMailPanel();
                    mailPanel.setReceiver("");
                    mailPanel.setSubject("Forward: " + message.getSubject());
                    mailPanel.setContent(BoxPanel.this.createReturnContent(
                            message));
                    BoxPanel.this.parent.changeMainPanel("mail");
                }
            }
            
        });
    }
    
    
    /**
     * To create message content for returned mail
     * @param message
     * @return
     */
    public String createReturnContent(MailMessage message) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\n\n");
        buffer.append("-------------------------------------------------------"
                + "-----------------------------------------------------------"
                + "-----------------------------------------------------------"
                + "----\n");
        buffer.append("From: " + message.getFrom() + "\n");
        buffer.append("To: " + message.getTo() + "\n");
        buffer.append("Time: " + message.getTime() + "\n");
        buffer.append("Subject: " + message.getSubject() + "\n\n");
        buffer.append(message.getContent());
        return buffer.toString();
    }
    
    
    /**
     * To get the type of this boxPanel
     * @return
     */
    public BoxType getBoxType() {
        return this.boxType;
    }
    
    
    /**
     * To set number index mail read
     * @param index
     */
    public void readMail(int index) {
        this.messageList.get(index).setReaded(true);
    }
}
