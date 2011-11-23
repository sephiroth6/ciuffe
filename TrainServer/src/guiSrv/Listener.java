/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSrv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import trainserver.*;

/**
 *
 * @author Claudio
 */
public class Listener implements Runnable {
    
    private ServerSocket ss;
    private Socket sh;
        
    public Listener(ServerSocket ss) {
        this.ss = ss;
        
    }
    
    @Override
    public void run () {
        try {    
        
            while (true){
                System.out.println("In attesa di chiamate dai Client... ");
                sh=ss.accept();
                System.out.println("Ho ricevuto una chiamata di apertura da:\n" + sh);
                new TrainServer(sh).start();
            }
        } catch (IOException ex) {}
    } 
        
}
    

