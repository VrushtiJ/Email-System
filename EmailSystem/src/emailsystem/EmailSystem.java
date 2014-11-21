/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsystem;

/**
 *
 * @author admin
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.event.ActionEvent;
public class EmailSystem extends javax.swing.JFrame{

        JLabel l1,l2,l3;
        JTextField f1;
        JPasswordField p1;
        JButton b1, b2;
           int flag=0;
           int flag_user=0;
            String user;
        public EmailSystem()
        {
             setTitle("Email-Chat Application");
             setVisible(true);
             setSize(800,800);
             setLayout(null);
             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            l3=new JLabel("Login for Application");
             l3.setForeground(Color.red);
             l3.setFont(new Font("Serif", Font.BOLD, 30));
             l1=new JLabel("Enter Username: ");
             l2=new JLabel("Enter Password: ");
             f1=new JTextField();
             p1=new JPasswordField();
             b1=new JButton("Login");
             b2=new JButton("Sign up");
            l3.setBounds(200, 100, 450, 50);
            l1.setBounds(190, 200, 200, 40);
            l2.setBounds(190, 270, 200, 40);
            f1.setBounds(300, 200, 300, 40);
            p1.setBounds(300, 270, 300, 40);
            b1.setBounds(200, 400, 200, 40);
            b2.setBounds(450, 400, 200, 40);
            add(l3);
            add(l1);
            add(f1);
            add(l2);
            add(p1);
            add(b1);
            add(b2);
            b2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            
              setVisible(false);
              Signup sign=new Signup();
            sign.setVisible(true);
              }
      });
      
         b1.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){   
            
             String user=f1.getText();
            String pass=new String(p1.getPassword());
             int f=0;
             if(f1.getText().equals("admin") && p1.getText().equals("admin"))
                        {
                            flag=1;
                                   String[] arguments = new String[] {};
         JOptionPane.showMessageDialog(null, "Server is started by Admin Now any user can chat");
                                  setVisible(false);
                                   new ChatServerSync();
                                   System.out.println("enter");
                                                             new EmailSystem();
                        }
             else
             {
                try
                {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/emailchat?zeroDateTimeBehavior=convertToNull","root", "Joshi94");
                    Statement Username=(Statement) c.createStatement();
                    ResultSet U=Username.executeQuery("SELECT username FROM information");
                    Statement Password=(Statement) c.createStatement();
                    ResultSet P=Password.executeQuery("SELECT password FROM information");
                    while(U.next() && P.next())
                    {
                        if(f1.getText().equals(U.getString(1)) && p1.getText().equals(P.getString(1)))
                        {
                              System.out.println(U.getString(1)+" string "+P.getString(1));
                       
                              user=f1.getText();
                                  f=1;
                                  setVisible(false);
                            Login log=new Login(f1.getText());
                            log.setVisible(true);                            
                        }
                    }
                    if(f==0)
                    {
                        JOptionPane.showMessageDialog(rootPane, "Wrong Password or sign up..!!");
                    
                    }
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                    ex.printStackTrace();
            
                  }
            }
         }
        });
   
}       /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         
        new EmailSystem();
        
    }
}
