/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsystem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame {
    JLabel choose;
    JButton mail, chat, signout; 
    
    Login(String User)
    {
        setTitle("Applications");
        setVisible(true);
        setLayout(null);
        setSize(500,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        choose=new JLabel("Enjoy chatting and mailing...!! :)");
        choose.setForeground(Color.blue);
        choose.setFont(new Font("Serif", Font.BOLD, 30));
        mail=new JButton("Send an Email with attachments");
        chat=new JButton("Do you wanna chat?");
        signout=new JButton("Signout");
        
        choose.setBounds(50, 100, 500, 30);
        mail.setBounds(120, 250, 300, 30);
        chat.setBounds(120, 300, 250, 30);
        signout.setBounds(350, 150, 80, 30);
        
        add(choose);
        add(mail);
        add(chat);
        add(signout);
        mail.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
            Mail m=new Mail();
            m.setVisible(true);
        }
        });
        chat.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
        String name=null;
                  try
                {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/emailchat?zeroDateTimeBehavior=convertToNull","root", "Joshi94");
                    Statement Username=(Statement) c.createStatement();
                    ResultSet U=Username.executeQuery("SELECT username FROM information");
                    while(U.next())
                    {
                        if(User.equals(U.getString("username")))
                        {
                            name=User;
                            break;
                        }
                    }
             //       new ChatServerSync();
                   ClientServer C=new ClientServer(name);
                    
               }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                    ex.printStackTrace();
            
                  }
            }
        });
        
            
        signout.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                EmailSystem obj=new EmailSystem();
                obj.setVisible(true);
            }
        });
    }
    
}
// InetAddress add=InetAddress.getLocalHost();
//String ip=add.getHostAddress();