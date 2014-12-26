package com.wt.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.wt.utils.Manager;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private int width = 300;
    private int height = 200;
    
    private JPanel jp1, jp2, jp3, jp4;
    private JLabel userLabel;
    private JLabel passLabel;
    private JLabel serverLabel;
    private JTextField userField;
    private JPasswordField passField;
    private JTextField serverField;
    private JButton okBut;
    private JButton cancelBut;
    
    public LoginFrame() {
        super();
        initFrame();  
    }
    
    private void initFrame() {
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        userLabel = new JLabel("username");
        passLabel = new JLabel("password");
        serverLabel = new JLabel("server");
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        serverField = new JTextField(15);
        okBut = new JButton("login");
        cancelBut = new JButton("cancel");
        
        
        this.setLayout(new GridLayout(4, 1));
        jp1.add(userLabel);
        jp1.add(userField);
        
        jp2.add(passLabel);
        jp2.add(passField);
        
        jp3.add(serverLabel);
        jp3.add(serverField);
        
        jp4.add(okBut);
        jp4.add(cancelBut);
        
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        
        this.setButtonListener();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(this.width, this.height);
        this.setTitle("Login");
        this.setVisible(true);
    }
    
    private void setButtonListener() {
        okBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Manager.username = userField.getText();
                Manager.password = new String(passField.getPassword());
                Manager.server = serverField.getText();
                if (Manager.auth()) {
                    Manager.writeData();
                    new MainFrame();
                    dispose();
                }
                else {
                    
                }
            }
        });
        
        cancelBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                userField.setText("");
                passField.setText("");
                serverField.setText("");
            }
            
        });
    }
}
