package train;

//Importo i package
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import lib.*;

//Creazione di una classe per il Multrithreading
public class TrainServer extends Thread {

    private Socket socket;
    Archivio archivio; //archivio totale
    ArrayList<Treno> treni = new ArrayList();
    ArrayList<Treno> ricercaTreni = new ArrayList();//contiene tutti i treni
    ArrayList<Prenotazione> prenotazioni = new ArrayList(); //tutte le prenotazioni
    ArrayList<Prenotazione> prenotazioniEff = new ArrayList();//ricerca preno
    ArrayList<Prenotazione> visualizzaPrenotazioni = new ArrayList(); // per visualizzare e poi annullare
    ArrayList<Prenotazione> eliminaPrenotazioni = new ArrayList(); //cancella preno
    JTextArea jt;

    public TrainServer(Socket socket, JTextArea text, Archivio arch) throws FileNotFoundException {
        this.socket = socket;
        jt = text;
        archivio = arch;
      //  archivio.creaArchivioTreni();
      //  archivio.creaArchvioPrenotazioni();
        treni = archivio.getArchivioTreni();
        prenotazioni = archivio.getArchivioPrenotazioni();
    }

    //esecuzione del Thread sul Socket
    @Override
    public void run () {
        boolean run = true;
        ObjectInputStream objectInputStream;
        ObjectOutputStream obOs;
        int a=0;
        int b=0;
        
        try {
            
            Prenotazione p=null;
            while (run) {
                
               
               
                try {
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    p = (Prenotazione)objectInputStream.readObject();
                //    objectInputStream.close();
                   
                    
                    if(p!=null){
                        if(p.getCodicePrenotazione().equals("") && p.getCodiceTreno().equals("")){
                            
                            ricercaTreni = archivio.getArrayListTratta(p.getStazionePartenza(), p.getStazioneArrivo(), p.getDataPartenza(), p.getPostoPrenotato());
                          
                             obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(ricercaTreni); 
                            obOs.flush();
                            
                            
                        }
                        if(p.getCodicePrenotazione().equals("") && !p.getCodiceTreno().equals("")){
                           
                            Treno trenino=null;
                            for(int i=0; i<treni.size();i++){
                                if(treni.get(i).getCodiceTreno().equals(p.getCodiceTreno()))
                                    trenino=treni.get(i);
                                
                            }
                            prenotazioniEff = archivio.prenotaMultipla(p.getPostoPrenotato(), p, trenino);
                            
                            obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(prenotazioniEff); 
                       
                             obOs.flush();
                           
                        
                        }
                         if(!p.getCodicePrenotazione().equals("") && !p.getCodicePrenotazione().equals("conferma")){
                           
                            visualizzaPrenotazioni = archivio.visualizzaPrenotazione(p);
                            
                            obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(visualizzaPrenotazioni); 
                       
                            obOs.flush();
                           
                        
                        }
                         
                         if(!p.getCodicePrenotazione().equals("") && p.getNomeCliente().equals("conferma")){
                           
                            eliminaPrenotazioni = archivio.eliminaPrenotazione(p);
                            
                            obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(eliminaPrenotazioni); 
                       
                            obOs.flush();
                           
                        
                        }
                        
                        
                        
                        
                        
                        
                    }
                }
                catch (ClassNotFoundException ex) {
                    Logger.getLogger(TrainServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (p==null) {
                   
                    run = false;
                    
                }
           
              
            }
            
            
            jt.append("Ho ricevuto una chiamata di chiusura da:\n" + socket + "\n");
            socket.close();
        } catch (IOException e) {
            jt.append("IOException: " + e +"\n");
        }
    }
    
    
}
    
    
