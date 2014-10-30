/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsystem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.event.ActionEvent;
/**
 *
 * @author admin
 */
public class Signup extends JFrame {
    
    JLabel signup, username, password, con_pass, Full_name, mail;
    JTextField user, full;
    JPasswordField pass, con;
    JButton submit;
    Signup()
    {
        setTitle("SignUp");
        setVisible(true);
        setSize(900,800);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        signup=new JLabel("Sign Up");
        signup.setForeground(Color.blue);
        signup.setFont(new Font("Serif", Font.BOLD, 40));
        Full_name=new JLabel("Enter your name: ");
        username=new JLabel("Enter unique Username: ");
        password=new JLabel("Enter Password: ");
        con_pass=new JLabel("Confirm your Password: ");
        submit=new JButton("Submit");
        
        full=new JTextField();
        user=new JTextField();
        pass=new JPasswordField();
        con=new JPasswordField();
        
        signup.setBounds(400, 70, 150, 40);
        Full_name.setBounds(220,150,200,30);
        full.setBounds(380,150,300,30);
        username.setBounds(220, 200, 300, 30);
        user.setBounds(380, 200, 300, 30);
        password.setBounds(220, 250, 200, 30);
        pass.setBounds(380, 250, 300, 30);
        con_pass.setBounds(220, 300, 200, 30);
        con.setBounds(380, 300, 300, 30);
        submit.setBounds(400, 500, 100, 30);
        
        System.out.println("vvv");
       add(signup);
       add(Full_name);
       add(full); 
       add(username);
       add(user);
       add(password);
       add(pass);
       add(con_pass);
       add(con);
       add(submit);
       
       submit.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e)
          {
              if(con.getText().equals(pass.getText()))
              {
                  try{
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/emailchat?zeroDateTimeBehavior=convertToNull","root", "Joshi94");
                    Statement st=(Statement) c.createStatement();
                    String str="INSERT INTO information(username,password,confirmpassword,full_name) values ('"+user.getText()+"', '"+pass.getText()+"','"+con.getText()+"','"+full.getText()+"')";
                   st.executeUpdate(str);
                   JOptionPane.showMessageDialog(null, user.getText()+"  " + full.getText() + "Added Successfully ....!!");
                   setVisible(false);
                   Login obj=new Login();
                   
                   obj.setVisible(true);
                  }
                   catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                    ex.printStackTrace();
            
                  }
              }
              else
              {
                      JOptionPane.showMessageDialog(rootPane, "Wrong Password..!!");
                    
              }
          }
       });
    }
    
    
}
