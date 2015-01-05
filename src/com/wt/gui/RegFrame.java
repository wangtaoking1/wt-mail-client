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


/**
 * RegFrame the frame for users register
 * @author wangtao
 * @time 2014/12/25
 */
public class RegFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private final int WIDTH = 300;
    private final int HEIGHT = 200;
    private JPanel jp1, jp2, jp3, jp4;
    private JLabel userLabel;
    private JLabel passLabel;
    private JLabel confirmLabel;
    private JTextField userField;
    private JPasswordField passField;
    private JPasswordField confirmField;
    private JButton okBut;
    private JButton cancelBut;
    
    public RegFrame() {
        super();
        initFrame();
    }
    
    private void initFrame() { 
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        
        userLabel = new JLabel("用户名 ");
        passLabel = new JLabel("密码   ");
        confirmLabel = new JLabel("密码确认");
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        confirmField = new JPasswordField(15);
        okBut = new JButton("注册");
        cancelBut = new JButton("返回");
        
        
        this.setLayout(new GridLayout(4, 1));
        jp1.add(userLabel);
        jp1.add(userField);
        
        jp2.add(passLabel);
        jp2.add(passField);
        
        jp3.add(confirmLabel);
        jp3.add(confirmField);
        
        jp4.add(okBut);
        jp4.add(cancelBut);
        
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        
        this.setActionListeners();
        
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = (int)screensize.getWidth();
        int screenH = (int)screensize.getHeight();
        this.setLocation((screenW - this.WIDTH) / 2, (screenH - this.HEIGHT) / 2);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(this.WIDTH, this.HEIGHT);
        this.setTitle("注册");
        this.setVisible(true);
    }
    
    
    private void setActionListeners() {
        this.confirmField.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent arg0) {
                // TODO Auto-generated method stub
                if (arg0.getKeyCode() != arg0.VK_ENTER)
                    return ;
                if (checkBlank()) {
                    RegFrame.this.register();
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
                if (checkBlank()) {
                    RegFrame.this.register();
                }
            }
        });
        
        okBut.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent arg0) {
                // TODO Auto-generated method stub
                if (arg0.getKeyCode() != arg0.VK_ENTER)
                    return ;
                if (checkBlank()) {
                    RegFrame.this.register();
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
        
        cancelBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame();
                dispose();
            }
            
        });
    }
    
    
    /**
     * To register user from the GUI
     */
    private void register() {
        Manager.username = userField.getText();
        Manager.password = new String(passField.getPassword());
        String confirmP = new String(confirmField.getPassword());
        
        if (!confirmP.equals(Manager.password)) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Error password", 
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            passField.setText("");
            confirmField.setText("");
            return ;
        }
        
        if (Manager.userExists(Manager.username)) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "User Existed", 
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            userField.setText("");;
            passField.setText("");;
            confirmField.setText("");
        }
        else {
            if (Manager.regUser(Manager.username, Manager.password)) {
                new LoginFrame();
                dispose();
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, 
                        "Register successfully", "", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Register Failed", 
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                userField.setText("");;
                passField.setText("");;
                confirmField.setText("");
            }
        }
    }
    
    
    /**
     * To check whether or not there are blanks
     * @return
     */
    private boolean checkBlank() {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        String confirm = new String(confirmField.getPassword());
        
        if (confirm.equals("") || username.equals("") 
                || password.equals("")) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "No blanks", 
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
