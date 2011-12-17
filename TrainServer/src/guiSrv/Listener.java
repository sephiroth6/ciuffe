/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSrv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import train.*;

/**
 *
 * @author angelo
 */
public class Listener implements Runnable {

    private ServerSocket ss;
    private ArrayList<Socket> sh= new ArrayList();
    private JTextArea jt;
    Archivio archivio;


    public Listener(ServerSocket ss, JTextArea text, String a, String p) throws FileNotFoundException {
        
        this.ss = ss;
        jt = text;
        archivio = new Archivio(a, p);
        archivio.creaArchivioTreni();
        archivio.creaArchvioPrenotazioni();

    }

    @Override
    public void run() {
        try {

            while (true) {
                jt.append("In attesa di chiamate dai Client...\n");
                Socket suk = ss.accept();
                sh.add(suk);
                jt.append("Ho ricevuto una chiamata di apertura da:\n" + suk + "\n");
                //jt.append("Ho ricevuto una chiamata di apertura da:\n" + sh.get(sok-1) + "\n");

                new TrainServer(suk, jt, archivio).start();

//                if (ss.accept().isClosed()) {
//                    jt.append("Ho ricevuto una chiamata di chiusura da:\n" + ss.accept() + "\n");
//                }
            }
        } catch (IOException ex) {}
//        finally{
//            try {
//                ss.close();
//            } catch (IOException ex) {
//                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public Archivio getArch(String arch, String pren) {
        if(archivio.setPath(arch, pren)){
            return archivio;
        }
        jt.append("Errore nella lettura del file!\n");
        return null;
    }

    public void closeAll() throws IOException {


        
        for(int i=0; i<sh.size(); i++){
            if(sh.get(i) !=null)
                sh.get(i).close();
        }
        sh.removeAll(sh);
        ss.close();
//        
//        if (sh!=null) {
//            sh.close();
//        }

    }
}
