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
    Archivio archivio = new Archivio(); //archivio totale
    ArrayList<Treno> treni = new ArrayList();
    ArrayList<Treno> ricercaTreni = new ArrayList();//contiene tutti i treni
    ArrayList<Prenotazione> prenotazioni = new ArrayList(); //tutte le prenotazioni
<<<<<<< HEAD
    ObjectInputStream objectInputStream;
=======
>>>>>>> 5cb68f2da6eb7629bc75e7db3e22e1e312de37a5

    public TrainServer(Socket socket) throws FileNotFoundException {
        this.socket = socket;
        
        archivio.creaArchivioTreni();
        archivio.creaArchvioPrenotazioni();
        treni = archivio.getArchivioTreni();
        prenotazioni = archivio.getArchivioPrenotazioni();
<<<<<<< HEAD
        
=======
>>>>>>> 5cb68f2da6eb7629bc75e7db3e22e1e312de37a5
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
                
               objectInputStream = new ObjectInputStream(socket.getInputStream());
               
                try {
                    
                    p = (Prenotazione)objectInputStream.readObject();
<<<<<<< HEAD
                   
                    if(p!=null){
                        p.stampaPrenotazione();
                       // if(p.getCodicePrenotazione().equals("")){
                         //   ricercaTreni = archivio.getArrayListTratta(p.getStazionePartenza(), p.getStazioneArrivo(), p.getDataPartenza());
                          //  ObjectOutputStream obOs = new ObjectOutputStream(socket.getOutputStream());
                         //   obOs.writeObject(ricercaTreni);
                       // }
=======
                    if(p!=null){
                        if(p.getCodicePrenotazione().equals("")){
                            ricercaTreni = archivio.getArrayListTratta(p.getStazionePartenza(), p.getStazioneArrivo(), p.getDataPartenza());
                            ObjectOutputStream obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(ricercaTreni);
                        }
>>>>>>> 5cb68f2da6eb7629bc75e7db3e22e1e312de37a5
                        
                        
                    }
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