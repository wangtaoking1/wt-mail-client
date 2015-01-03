package com.wt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.wt.utils.MailMessage;

public class MailItemPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private BoxPanel parent;
    private JLabel senderLab, timeLab, subjectLab;
    private JPanel upPanel, downPanel;
    
    public MailItemPanel(BoxPanel boxPanel) {
        super();
        this.parent = boxPanel;
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
        
        this.addActionListeners();
    }
    
    
    /**
     * To update the UI
     * @param sender
     * @param time
     * @param subject
     */
    public void updateMails(String sender, String time, String subject) {
        this.senderLab.setText(sender);
        this.timeLab.setText(time);
        this.subjectLab.setText(subject);
        
        this.updateUI();
    }
    
    
    public void addActionListeners() {
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                // TODO Auto-generated method stub
                if (MailItemPanel.this.getBackground() == Color.BLACK) {
                    Component[] comps = MailItemPanel.this.parent.getAllMailItems();
                    int cnt = 0;
                    for (cnt = 0; cnt < comps.length; cnt++) {
                        if (MailItemPanel.this.equals(comps[cnt])) {
                            break;
                        }
                    }
                    if (cnt != comps.length) {
                        new MailInfoFrame(MailItemPanel.this.parent
                                .getMessage(cnt));
                    }
                    return ;
                }
                MailItemPanel.this.parent.removeAllFocus();
                MailItemPanel.this.setBackground(Color.BLACK);
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }
            
        });
    }
}
