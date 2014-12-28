package com.wt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    
    private JPanel headPanel, leftPanel, mainPanel;
    
    //head
    private JLabel logoutLab;
    private JLabel accountLabel;
    
    private JLabel sendMailLab;
    private JLabel receiveLab;
    private JLabel sendLab;
    
    public MainFrame() {
        super();
        
        headPanel = new JPanel();
        leftPanel = new JPanel();
        mainPanel = new JPanel();
        
        logoutLab = new JLabel("logout");
        accountLabel = new JLabel("test0");
        
        headPanel.add(accountLabel);
        headPanel.add(logoutLab);
        headPanel.setBackground(Color.GRAY);
        headPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        
        JPanel jp1, jp2, jp3;
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        
        sendMailLab = new JLabel("send mail");
        jp1.add(sendMailLab);
        receiveLab = new JLabel("receive box");
        jp2.add(receiveLab);
        sendLab = new JLabel("send box");
        jp3.add(sendLab);
        
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(jp1);
        leftPanel.add(jp2);
        leftPanel.add(jp3);
        leftPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        //leftPanel.setBackground(Color.darkGray);
        
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, headPanel);
        this.add(BorderLayout.WEST, leftPanel);
        this.add(BorderLayout.CENTER, mainPanel);
        
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
}
