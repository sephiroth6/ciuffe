/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSrv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;
import train.*;

/**
 *
 * @author Claudio
 */
public class Listener implements Runnable {
    
    private ServerSocket ss;
    private Socket sh;
    private JTextArea jt;
    Archivio archivio;
        
    public Listener(ServerSocket ss, JTextArea text) throws FileNotFoundException {
        this.ss = ss;
        jt = text;
        archivio = new Archivio();
        archivio.creaArchivioTreni();
        archivio.creaArchvioPrenotazioni();
    }
    
    @Override
    public void run () {
        try {    
        
            while (true){
                jt.append("In attesa di chiamate dai Client...\n");
                sh=ss.accept();
                jt.append("Ho ricevuto una chiamata di apertura da:\n" + sh + "\n");
                
                new TrainServer(sh, jt, archivio).start();
                
                if (sh.isClosed())
                  jt.append("Ho ricevuto una chiamata di chiusura da:\n" + sh + "\n");  
            }
        } catch (IOException ex) {}
    } 
    
    public void closeAll() throws IOException{
        
        ss.close();
        sh.close();
        
    }

    
        
}
    

