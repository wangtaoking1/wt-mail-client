package com.wt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.wt.smtp.SMTPRunnable;
import com.wt.utils.MailMessage;
import com.wt.utils.Manager;
import com.wt.utils.User;

public class SendMailPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JPanel infoPanel, sendPanel, upPanel, centerPanel, downPanel;
    
    private JLabel receiverLab, subjectLab, contentLab, fileLab;
    private JTextField receiverField, subjectField;
    private JTextArea contentArea;
    private JButton sendBut;
    private JScrollPane scrollPane;
    private ArrayList<String> fileList = null;
    
    public SendMailPanel() {
        super();
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        
        infoPanel = new JPanel();
        sendPanel = new JPanel();
        upPanel = new JPanel();
        centerPanel = new JPanel();
        downPanel = new JPanel();
        infoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        sendPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        upPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        centerPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        downPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        centerPanel.setLayout(new BorderLayout());
        
        upPanel.add(sendPanel);
        upPanel.add(infoPanel);
        this.add(BorderLayout.NORTH, upPanel);
        this.add(BorderLayout.CENTER, centerPanel);
        this.add(BorderLayout.SOUTH, downPanel);
        
        sendBut = new JButton("发送");
        sendPanel.add(sendBut);
        
        JPanel panel1 = new JPanel();
        receiverLab = new JLabel("收件人");
        receiverField = new JTextField(50);
        panel1.add(receiverLab);
        panel1.add(receiverField);
        infoPanel.add(panel1);
        
        JPanel panel2 = new JPanel();
        subjectLab = new JLabel("主题   ");
        subjectField = new JTextField(50);
        panel2.add(subjectLab);
        panel2.add(subjectField);
        infoPanel.add(panel2);
        
        JPanel panel3 = new JPanel();
        contentLab = new JLabel("正文");
        panel3.add(contentLab);
        centerPanel.add(BorderLayout.NORTH, panel3);
        contentArea = new JTextArea();
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(contentArea);
        centerPanel.add(BorderLayout.CENTER, scrollPane);
        
        fileLab = new JLabel("添加附件");
        downPanel.add(fileLab);
        
        this.addActionListeners();
    }
    
    private void addActionListeners() {
        fileLab.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
                fileLab.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub
                fileLab.setForeground(Color.BLACK);
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
        
        sendBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if (!SendMailPanel.this.checkValidation()) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "receivers invalid!!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return ;
                }
                ArrayList<MailMessage> messageList = SendMailPanel.this
                        .getMessage();
                for (MailMessage message: messageList) {
                    new Thread(new SMTPRunnable(message)).start();
                }
                
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Send successfully", 
                        "", JOptionPane.INFORMATION_MESSAGE);
                
                SendMailPanel.this.clearContent();
            }
            
        });
    }
    
    private void clearContent() {
        this.receiverField.setText("");
        this.subjectField.setText("");
        this.contentArea.setText("");
    }
    
    
    /**
     * To get the message from the GUI
     * @return
     */
    private ArrayList<MailMessage> getMessage() {
        ArrayList<MailMessage> list = new ArrayList<MailMessage>();
        
        String from = Manager.username + "@" + Manager.server;
        User user = new User(Manager.username, Manager.password);
        StringBuffer buffer = new StringBuffer();
        buffer.append("from: " + from + "\n");
        buffer.append("to: " + this.receiverField.getText() + "\n");
        buffer.append("subject: " + this.subjectField.getText() + "\n");
        buffer.append('\n');
        buffer.append(this.contentArea.getText());
        String[] tos = this.receiverField.getText().split(";");
        for (String to : tos) {
            MailMessage message = new MailMessage();
            message.setUser(user);
            message.setFrom(Manager.username + "@" + Manager.server);
            message.setTo(to);
            message.setContent(buffer.toString());
            list.add(message);
        }
        
        return list;
    }
    
    
    /**
     * To check the validation of mail receivers
     * @return
     */
    private boolean checkValidation() {
        String tos = this.receiverField.getText();
        String[] receivers = tos.split(";");
        boolean flag = true;
        for (String receiver : receivers) {
            System.out.println(receiver);
            if (!Pattern.matches("^\\w+@\\w+(\\.\\w+)*$", receiver)) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
