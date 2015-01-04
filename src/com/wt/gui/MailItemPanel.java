package com.wt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.wt.gui.BoxPanel.BoxType;
import com.wt.pop3.POPClient;
import com.wt.utils.MailMessage;

/**
 * MailItemPanel is the panel to show the header of a mail
 * @author wangtao
 * @time 2014/12/25
 */
public class MailItemPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public BoxPanel parent;
    private JLabel replyLab, timeLab, subjectLab;
    private JPanel upPanel, downPanel;
    
    public MailItemPanel(BoxPanel boxPanel) {
        super();
        this.parent = boxPanel;
        
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        
        upPanel = new JPanel();
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        downPanel = new JPanel();
        downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
        
        this.add(upPanel);
        this.add(downPanel);
        
        replyLab = new JLabel("From: test0@qq.com");
        upPanel.add(replyLab);
        upPanel.add(Box.createHorizontalGlue());
        timeLab = new JLabel("2014-12-28 20:30:45");
        upPanel.add(timeLab);
        
        subjectLab = new JLabel("hello world");
        subjectLab.setFont(new Font("", Font.BOLD, 13));
//        subjectLab.setForeground(Color.GRAY);
        downPanel.add(subjectLab);
        downPanel.add(Box.createHorizontalGlue());
        
        this.addActionListeners();
    }
    
    
    /**
     * To update the mail item UI
     * @param sender
     * @param time
     * @param subject
     */
    public void updateMails(String reply, String time, String subject) {
        if (this.parent.getBoxType() == BoxType.RECEIVEBOX)
            this.replyLab.setText("From: " + reply);
        else
            this.replyLab.setText("To: " + reply);
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
                        
                        if (MailItemPanel.this.parent.getBoxType() == 
                                BoxType.RECEIVEBOX) {
                            POPClient client = new POPClient();
                            client.readMail(cnt + 1);
                            MailItemPanel.this.parent.readMail(cnt);
                            MailItemPanel.this.parent.updateMessageUI();
                        }
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
    
    
    /**
     * To set the mail which has read on gray color
     */
    public void setHasReadUI() {
        this.replyLab.setForeground(Color.GRAY);
        this.timeLab.setForeground(Color.GRAY);
        this.subjectLab.setForeground(Color.GRAY);
    }
    
    
    /**
     * To set the mail which was not read on black color
     */
    public void setUnReadUI() {
        this.replyLab.setForeground(Color.BLACK);
        this.timeLab.setForeground(Color.BLACK);
        this.subjectLab.setForeground(Color.BLACK);
    }
}
