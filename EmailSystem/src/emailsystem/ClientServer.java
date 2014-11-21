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
 
//Class to precise who is connected : Client or Server
public class ClientServer {

    JButton Client, Server;
	ClientServer(String username){
		
                    String IPServer = JOptionPane.showInputDialog("Enter the Server ip adress");
                        String[] arguments = new String[] {IPServer};
			new ChatClient().main(arguments, username);
               
	}
     

}