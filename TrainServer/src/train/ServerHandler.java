/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package train;

/**
 *
 * @author albyreturns
 * 
 * 
 */

import java.net.*;

public class ServerHandler {
    
     public void start() throws Exception {
         ServerSocket serverSocket = new ServerSocket(5001);

         //Ciclo infinito di ascolto dei Client
         while(true) {
             System.out.println("In attesa di chiamate dai Client... ");
             Socket socket = serverSocket.accept();
             System.out.println("Ho ricevuto una chiamata di apertura da:\n" + socket);
             TrainServer server = new TrainServer(socket);
             server.start();
           }
     }

     public static void main (String[] args) throws Exception   {
         ServerHandler tcpServer = new ServerHandler();
         tcpServer.start();
     }
  }