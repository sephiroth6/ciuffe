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
            
                sh=ss.accept();
                new TrainServer(sh).start();
            }
        } catch (IOException ex) {}
    } 
        
}
    

