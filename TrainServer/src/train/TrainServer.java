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
    Archivio archivio = new Archivio(); //archivio totale
    ArrayList<Treno> treni = new ArrayList();
    ArrayList<Treno> ricercaTreni = new ArrayList();//contiene tutti i treni
    ArrayList<Prenotazione> prenotazioni = new ArrayList(); //tutte le prenotazioni
    ArrayList<Prenotazione> prenotazioniEff = new ArrayList();//ricerca preno
    JTextArea jt;

    public TrainServer(Socket socket, JTextArea text) throws FileNotFoundException {
        this.socket = socket;
        jt = text;
        archivio.creaArchivioTreni();
        archivio.creaArchvioPrenotazioni();
        treni = archivio.getArchivioTreni();
        prenotazioni = archivio.getArchivioPrenotazioni();
    }

    //esecuzione del Thread sul Socket
    @Override
    public void run () {
        boolean run = true;
        
        try {
            
            Prenotazione p=null;
            while (run) {
                
               
               
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    p = (Prenotazione)objectInputStream.readObject();
                    
                    p.stampaPrenotazione();
                    
                    if(p!=null){
                        if(p.getCodicePrenotazione().equals("") && p.getCodiceTreno().equals("")){
                            ricercaTreni = archivio.getArrayListTratta(p.getStazionePartenza(), p.getStazioneArrivo(), p.getDataPartenza(), p.getPostoPrenotato());
                            
                            ObjectOutputStream obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(ricercaTreni); 
                            
                        }
                        if(p.getCodicePrenotazione().equals("") && !p.getCodiceTreno().equals("")){
                            System.out.println("sto qui");
                            Treno trenino=null;
                            for(int i=0; i<treni.size();i++){
                                if(treni.get(i).getCodiceTreno().equals(p.getCodiceTreno()))
                                    trenino=treni.get(i);
                                System.out.println(i);
                            }
                            prenotazioniEff = archivio.prenotaMultipla(p.getPostoPrenotato(), p, trenino);
                            
                            ObjectOutputStream obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(prenotazioniEff); 
                        
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
    
    
