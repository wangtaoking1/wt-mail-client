package com.wt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.wt.gui.BoxPanel.BoxType;
import com.wt.manage.Manager;
import com.wt.pop3.ReceiveBoxRunnable;
import com.wt.pop3.SendBoxRunnable;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    
    private JPanel mainPanel;
    private JPanel headPanel, leftPanel, mailPanel;
    private BoxPanel receivePanel, sendPanel;
    
    //head
    private JLabel logoutLab;
    private JLabel accountLabel;
    
    JPanel jp1, jp2, jp3;
    
    private JLabel sendMailLab;
    private JLabel receiveLab;
    private JLabel sendLab;
    
    public MainFrame() {
        super();
        
        if (!Manager.isLogin()) {
            new ServerFrame();
            this.dispose();
        }

        mainPanel = new JPanel();
        
        headPanel = new JPanel();
        headPanel.setBackground(Color.GRAY);
        headPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        
        receivePanel = new BoxPanel(this, BoxType.RECEIVEBOX);
        sendPanel = new BoxPanel(this, BoxType.SENDBOX);
        mailPanel = new SendMailPanel();
        
        this.setDaemonThread();
        
        logoutLab = new JLabel("注销");
        accountLabel = new JLabel(Manager.username + "@" + Manager.server);
        
        headPanel.add(accountLabel);
        headPanel.add(logoutLab);
        
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        
        receiveLab = new JLabel("   收件箱   ");
        jp1.add(receiveLab);
        sendLab = new JLabel("   发件箱   ");
        jp2.add(sendLab);
        sendMailLab = new JLabel("   发邮件   ");
        jp3.add(sendMailLab);
        
        leftPanel.add(jp1);
        leftPanel.add(jp2);
        leftPanel.add(jp3);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(BorderLayout.NORTH, headPanel);
        mainPanel.add(BorderLayout.WEST, leftPanel);
        mainPanel.add(BorderLayout.CENTER, receivePanel);
        receiveLab.setForeground(Color.RED);
        
        this.add(mainPanel);
        
        this.setActionListeners();
        
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = (int)screensize.getWidth();
        int screenH = (int)screensize.getHeight();
        this.setLocation((screenW - this.WIDTH) / 2, (screenH - this.HEIGHT)
                / 2);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(this.WIDTH, this.HEIGHT);
        this.setTitle("WT Mail Client");
        this.setVisible(true);
    }
    
    
    /**
     * To set daemon thread for receive box and send box
     */
    private void setDaemonThread() {
        Thread receiveThread = new Thread(new ReceiveBoxRunnable(
                this.receivePanel));
        receiveThread.setDaemon(true);
        receiveThread.start();
        
        Thread sendThread = new Thread(new SendBoxRunnable(
                this.sendPanel));
        sendThread.setDaemon(true);
        sendThread.start();
    }

    public void setActionListeners() {
        //logout
        logoutLab.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                Manager.logout();
                new ServerFrame();
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                logoutLab.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutLab.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
            
        });
        
        sendMailLab.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                //TODO
                MainFrame.this.changeMainPanel("mail");
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
            
        });
        
        receiveLab.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                //TODO
                MainFrame.this.changeMainPanel("receive");
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
            
        });
        
        sendLab.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                //TODO
                MainFrame.this.changeMainPanel("send");
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
            
        });
    }
    
    
    /**
     * To change the main panel
     * @param panel
     */
    public void changeMainPanel(String panel) {
        sendMailLab.setForeground(Color.BLACK);
        receiveLab.setForeground(Color.BLACK);
        sendLab.setForeground(Color.BLACK);
        
        
        mainPanel.remove(receivePanel);
        mainPanel.remove(sendPanel);
        mainPanel.remove(mailPanel);
        
        switch (panel) {
        case "receive":
            mainPanel.add(BorderLayout.CENTER, receivePanel);
            receiveLab.setForeground(Color.RED);
            break;
        case "send":
            mainPanel.add(BorderLayout.CENTER, sendPanel);
            sendLab.setForeground(Color.RED);
            break;
        case "mail":
            mainPanel.add(BorderLayout.CENTER, mailPanel);
            sendMailLab.setForeground(Color.RED);
            break;
        }
        
        mainPanel.updateUI();
    }
    
    
    public SendMailPanel getMailPanel() {
        return (SendMailPanel)this.mailPanel;
    }
    
    
    /**
     * To get the current Panel
     * @return
     */
    public String getCurrentPanel() {
        if (receiveLab.getForeground() == Color.RED)
            return "receive";
        else if (sendLab.getForeground() == Color.RED)
            return "send";
        else
            return "mail";
    }
}
