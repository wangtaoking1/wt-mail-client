package com.wt.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import com.wt.utils.MailMessage;

public class MailInfoFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    
    private MailMessage message = null;
    
    private JPanel upPanel, downPanel;
    private JLabel subjectLab, receiverLab, timeLab, senderLab;
    private JTextArea contentArea;
    private JScrollPane scrollPane;
    
    
    public MailInfoFrame(MailMessage message) {
        super();
        this.message = message;
        
        upPanel = new JPanel();
        upPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.Y_AXIS));
        downPanel = new JPanel();
        downPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.Y_AXIS));
        
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, upPanel);
        this.add(BorderLayout.CENTER, downPanel);
        
        subjectLab = new JLabel(this.message.getSubject());
        subjectLab.setFont(new Font("微软雅黑", Font.BOLD, 18));
        senderLab = new JLabel("发件人：" + this.message.getFrom());
        timeLab = new JLabel("发送时间：" + this.message.getTime());
        receiverLab = new JLabel("收件人：" + this.message.getTo());
        upPanel.add(subjectLab);
        upPanel.add(senderLab);
        upPanel.add(timeLab);
        upPanel.add(receiverLab);
        
        contentArea = new JTextArea();
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(contentArea);
        contentArea.setEditable(false);
        contentArea.setText(this.message.getContent());
        downPanel.add(scrollPane);
        
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = (int)screensize.getWidth();
        int screenH = (int)screensize.getHeight();
        this.setLocation((screenW - this.WIDTH) / 2, (screenH - this.HEIGHT)
                / 2);
        
        this.setResizable(false);
        this.setSize(this.WIDTH, this.HEIGHT);
        this.setTitle("WT Mail Client");
        this.setVisible(true);
    }
}
