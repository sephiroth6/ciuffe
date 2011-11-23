///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package guiSrv;
//
//import java.net.ServerSocket;
//import java.net.Socket;
//import trainserver.TrainServer;
//
///**
// *
// * @author angelo
// */
//public class Listener2 {
//    ServerSocket serverSocket;
//    TrainServer server;//new TrainServer(socket, serverSocket);
//    Socket s;
//    
//    public Listener(ServerSocket a, Socket b) {
//        this.so = a;
//        this.sh = b;
//    }
//    
//    while(true) {
//             System.out.println("In attesa di chiamate dai Client... ");
//             Socket socket = serverSocket.accept();
//             System.out.println("Ho ricevuto una chiamata di apertura da:\n" + socket);
//             TrainServer server = new TrainServer(socket, serverSocket);
//             server.start();
//           }
//    
//}
