/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsystem;

import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
/**
 *
 * @author admin
 */

// the Server class
public class ChatServerSync {
  private static ServerSocket serverSocket = null;
  private static Socket clientSocket = null;

  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int maxClientsCount = 10;
  private static final Thread_Client[] threads = new Thread_Client[maxClientsCount];

  
  ChatServerSync() {

    // The default port number.
    int portNumber = 1234;
      System.out.println("using port number=" + portNumber);
   
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
    while (true) {
        System.out.println("client");
      try {
        clientSocket = serverSocket.accept();
        System.out.println("Client new");
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {
          if (threads[i] == null) {
              System.out.println("Thread "+threads[i]);  
              (threads[i] = new Thread_Client(clientSocket, threads)).start();
            break;
          }
          
        }
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }  
}