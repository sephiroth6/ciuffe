/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainserver;

/**
 *
 * @author albyreturns
 * 
 * 
 */

import java.io.IOException;
import java.net.*;

public class ServerHandler {
    
    ServerSocket serverSocket = null;
    
//    public ServerHandler (ServerSocket s){
//        serverSocket=s;
//    }
    
    public void start() throws Exception {
         
        serverSocket = new ServerSocket(5001);
         //Ciclo infinito di ascolto dei Client
         while(true) {
             System.out.println("In attesa di chiamate dai Client... ");
             Socket socket = serverSocket.accept();
             System.out.println("Ho ricevuto una chiamata di apertura da:\n" + socket);
             TrainServer server = new TrainServer(socket);
             server.start();
           }
     }
    private void start2(){
        
    }
    
         
     public void stop() throws IOException{
         serverSocket.close();
     }
     

//     public static void main (String[] args) throws Exception   {
//         ServerHandler tcpServer = new ServerHandler();
//         tcpServer.start();
//     }
  }