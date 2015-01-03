package com.wt.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.wt.manage.Manager;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private final int WIDTH = 300;
    private final int HEIGHT = 200;
    private JPanel jp1, jp2, jp3;
    private JLabel userLabel;
    private JLabel passLabel;
    private JTextField userField;
    private JPasswordField passField;
    private JButton okBut;
    private JButton backBut;
    private JButton regBut;
    
    public LoginFrame() {
        super();
        initFrame();
    }
    
    private void initFrame() { 
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        userLabel = new JLabel("username");
        passLabel = new JLabel("password");
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        
        this.setDefaultValue();
        
        okBut = new JButton("login");
        backBut = new JButton("back");
        regBut = new JButton("register");
        
        this.setLayout(new GridLayout(3, 1));
        jp1.add(userLabel);
        jp1.add(userField);
        
        jp2.add(passLabel);
        jp2.add(passField);
        
        jp3.add(okBut);
        jp3.add(backBut);
        jp3.add(regBut);
        
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        
        this.setActionListeners();
        
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = (int)screensize.getWidth();
        int screenH = (int)screensize.getHeight();
        this.setLocation((screenW - this.WIDTH) / 2, (screenH - this.HEIGHT) / 2);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(this.WIDTH, this.HEIGHT);
        this.setTitle("Login");
        this.setVisible(true);
    }
    
    private void setActionListeners() {
        this.passField.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent arg0) {
                // TODO Auto-generated method stub
                if (arg0.getKeyCode() == arg0.VK_ENTER) {
                    Manager.username = userField.getText();
                    Manager.password = new String(passField.getPassword());
                    
                    if (Manager.auth(Manager.username, Manager.password)) {
                        Manager.writeData();
                        
                        new MainFrame();
                        dispose();
                    }
                    else {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, "Auth failed", "ERROR", 
                                JOptionPane.ERROR_MESSAGE);
                        Manager.username = null;
                        Manager.password = null;
                        userField.setText("");
                        passField.setText("");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyTyped(KeyEvent arg0) {
                // TODO Auto-generated method stub
                
            }
            
        });
        
        okBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Manager.username = userField.getText();
                Manager.password = new String(passField.getPassword());
                
                if (Manager.auth(Manager.username, Manager.password)) {
                    Manager.writeData();
                    
                    new MainFrame();
                    dispose();
                }
                else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Auth failed", "ERROR", 
                            JOptionPane.ERROR_MESSAGE);
                    Manager.username = null;
                    Manager.password = null;
                    userField.setText("");
                    passField.setText("");
                }
            }
        });
        
        backBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ServerFrame();
                dispose();
            }
            
        });
        
        regBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new RegFrame();
                dispose();
            }
            
        });
    }
    
    private void setDefaultValue() {
        if (Manager.username != null) {
            this.userField.setText(Manager.username);
        }
        if (Manager.password != null) {
            this.passField.setText(Manager.password);
        }
    }
}
