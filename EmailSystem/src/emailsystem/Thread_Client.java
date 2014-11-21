/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsystem;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author admin
 */

// For every client's connection we call this class
public class Thread_Client extends Thread{
     private String clientName = null;
  private DataInputStream is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final Thread_Client[] threads;
  private int maxClientsCount;
//Return_Username obj;
String username;
 // System.out.println(username);
  public Thread_Client(Socket clientSocket, Thread_Client[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
    
  }

  public void run() {
    int maxClientsCount = this.maxClientsCount;
    Thread_Client[] threads = this.threads;

    try {
      /*
       * Create input and output streams for this client.
       */
      is = new DataInputStream(clientSocket.getInputStream());
      os = new PrintStream(clientSocket.getOutputStream());
      String username;
    while (true) {
        os.println("Enter name you want to display on chat...");
        username = is.readLine().trim();
        if (username.indexOf('@') == -1) {
          break;
        } else {
          os.println("The name should not contain '@' character.");
        }
      }
      

      /* Welcome the new the client. */
      os.println( username+ " Entered in your chat .\nTo leave enter /quit in a new line.");
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] == this) {
            clientName =  username;
            break;
          }
        }
       
      }
      /* Start the conversation. */
      while (true) {
        String line = is.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
        /* If the message is private sent it to the given client. */
        if (line.startsWith("@")) {
          String[] words = line.split("\\s", 2);
          if (words.length > 1 && words[1] != null) {
            words[1] = words[1].trim();
            if (!words[1].isEmpty()) {
              synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                  if (threads[i] != null && threads[i] != this
                      && threads[i].clientName != null
                      && threads[i].clientName.equals(words[0])) 
                    threads[i].os.println("<" + username + "> " + words[1]);
                    /*
                     * Echo this message to let the client know the private
                     * message was sent.
                     */
                        this.os.println("<" + username + "> " + words[1]);
                    break;
                  }
                }
              }
            }
          
        } else {
          /* The message is public, broadcast it to all other clients. */
          synchronized (this) {
              for(int i=0;i< maxClientsCount;i++)
              {
                  if(threads[i]==this)
                  {
                      username = this.clientName;
                  }
              }
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println("<" + username + "> " + line);
              }
            }
          }
        }
      }
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] != this
              && threads[i].clientName != null) {
            threads[i].os.println("*** The user " + username
                + " is leaving the chat room !!! ***");
          }
        }
      }

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }
      /*
       * Close the output stream, close the input stream, close the socket.
       */
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }
  
}
