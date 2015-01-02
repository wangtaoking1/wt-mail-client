package com.wt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.wt.utils.MailMessage;

public class MailItemPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel senderLab, timeLab, subjectLab;
    private JPanel upPanel, downPanel;
    
    public MailItemPanel() {
        super();
        //this.setBackground(Color.BLACK);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        
        upPanel = new JPanel();
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        downPanel = new JPanel();
        downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
        
        this.add(upPanel);
        this.add(downPanel);
        
        
        senderLab = new JLabel("test0@qq.com");
        upPanel.add(senderLab);
        upPanel.add(Box.createHorizontalGlue());
        timeLab = new JLabel("2014-12-28 20:30:45");
        upPanel.add(timeLab);
        
        subjectLab = new JLabel("hello world");
        downPanel.add(subjectLab);
        downPanel.add(Box.createHorizontalGlue());
        
    }
}
