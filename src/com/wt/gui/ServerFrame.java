package com.wt.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.wt.utils.Manager;

public class ServerFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private final int WIDTH = 300;
    private final int HEIGHT = 200;
    
    private JPanel jp1, jp2;
    private JLabel serverLab;
    private JTextField serverField;
    private JButton okBut;
    private JButton cancelBut;
    
    public ServerFrame() {
        super();
        
        jp1 = new JPanel();
        jp2 = new JPanel();
        
        serverLab = new JLabel("server: ");
        serverField = new JTextField(15);
        
        this.setDefaultValue();
        
        okBut = new JButton("OK");
        cancelBut = new JButton("Cancel");
        
        jp1.add(serverLab);
        jp1.add(serverField);
        
        jp2.add(okBut);
        jp2.add(cancelBut);
        
        this.setLayout(new GridLayout(2, 1));
        this.add(jp1);
        this.add(jp2);
        
        this.setButtonListener();
        
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
    
    private void setButtonListener() {
        okBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Manager.server = serverField.getText();
                
                new LoginFrame();
                dispose();
            }
        });
        
        cancelBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                serverField.setText("");
                dispose();
            }
            
        });
    }
    
    private void setDefaultValue() {
        if (Manager.server!= null) {
            this.serverField.setText(Manager.server);
        }
    }
}
