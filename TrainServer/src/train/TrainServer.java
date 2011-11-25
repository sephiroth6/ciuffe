package train;

//Importo i package
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//Creazione di una classe per il Multrithreading
public class TrainServer extends Thread {

    private Socket socket;

    public TrainServer(Socket socket) throws FileNotFoundException {
        this.socket = socket;
        Archivio archivio=new Archivio();
         archivio.creaArchivioTreni();
         archivio.creaArchvioPrenotazioni();
         ArrayList<Treno> treni = new ArrayList();
         treni = archivio.getArchivioTreni();
         ArrayList<Prenotazione> prenotazioni = new ArrayList();
         prenotazioni = archivio.getArchivioPrenotazioni();
    }

    //esecuzione del Thread sul Socket
    @Override
    public void run () {
        boolean run = true;
        
        try {
            DataInputStream is = new DataInputStream(socket.getInputStream());
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            Prenotazione p=null;
            while (run) {
                //
                
               ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
               
                try {
                    p = (Prenotazione)objectInputStream.readObject();
                }
                catch (ClassNotFoundException ex) {
                    Logger.getLogger(TrainServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (p!=null) {
                   
                    run = false;
                }
           
              
            }
            
            os.close();
            is.close();
            System.out.println("Ho ricevuto una chiamata di chiusura da:\n" + socket + "\n");
            socket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
}