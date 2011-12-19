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
    private String utente;

    public TrainServer(Socket socket, JTextArea text, Archivio arch) throws FileNotFoundException {
        this.socket = socket;
        jt = text;
        archivio = arch;

        treni = archivio.getArchivioTreni();
        prenotazioni = archivio.getArchivioPrenotazioni();
        utente = "";
    }

    //esecuzione del Thread sul Socket
    @Override
    public void run() {
        boolean run = true;
        ObjectInputStream objectInputStream;
        ObjectOutputStream obOs;
        int a = 0;
        int b = 0;

        try {

            Prenotazione p = null;
            while (run) {



                try {
                    
                    // l'oggetto viene ricevuto in ingresso e convertito in una prenotazione
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    p = (Prenotazione) objectInputStream.readObject();

                    


                    if (p != null) {
                        
                        // richiesta di visualizzare una tratta
                        if (p.getCodicePrenotazione().equals("") && p.getCodiceTreno().equals("")) {

                            jt.append("Richiesta di tratta ricevuta da: " + utente + "\n");
                            jt.append(socket + "\n");
                            ricercaTreni = archivio.getArrayListTratta(p.getStazionePartenza(), p.getStazioneArrivo(), p.getDataPartenza(), p.getPostoPrenotato());

                            obOs = new ObjectOutputStream(socket.getOutputStream());

                            obOs.writeObject(ricercaTreni);
                            obOs.flush();



                        }
                        
                        // richiesta di prenotazione
                        if (p.getCodicePrenotazione().equals("") && !p.getCodiceTreno().equals("")) {

                            jt.append("Richiesta prenotazione ricevuta da: " + utente + "\n");
                            jt.append(socket + "\n");
                            Treno trenino = null;
                            for (int i = 0; i < treni.size(); i++) {
                                if (treni.get(i).getCodiceTreno().equals(p.getCodiceTreno())) {
                                    trenino = treni.get(i);
                                }

                            }
                            
                            prenotazioniEff = archivio.prenotaMultipla(p.getPostoPrenotato(), p, trenino);
                            
                            // invio di una arraylist in risposta alla richiesta di prenotazione
                            obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(prenotazioniEff);

                            obOs.flush();
                        }
                        
                        // salva il nome dell'operatore
                        if (!p.getCodicePrenotazione().equals("")
                                && p.getCodicePrenotazione().equals(p.getNomeCliente())) {

                           
                            utente = p.getCodicePrenotazione();
                          

                        }

                        // richiesta di visualizzazio di una prenotazione
                        if (!p.getCodicePrenotazione().equals("") && p.getNomeCliente().equals("prenota")) {


                            jt.append("Richiesta visualizzazione prenotazione effettuata da: " + utente + "\n");
                            jt.append(socket + "\n");
                            visualizzaPrenotazioni = archivio.visualizzaPrenotazione(p);
                            // arraylist contenente le prenotazioni desiderate
                            obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(visualizzaPrenotazioni);

                            obOs.flush();


                        }
                        if (!p.getCodicePrenotazione().equals("") && p.getNomeCliente().equals("ConfermaInvio")) {
                            // finalizza una prenotazione, la rende effettiva
                            archivio.finalizza(p.getCodicePrenotazione());


                            jt.append("Prenotazione effettuata da: " + utente + "\n");
                            jt.append(socket + "\n");


                        }
                        
                        // eliminazione in caso di mancata conferma
                        if (!p.getCodicePrenotazione().equals("") && p.getNomeCliente().equals("conferma")) {

                            jt.append("Prenotazione eliminata da: " + utente + "\n");
                            jt.append(socket + "\n");
                            eliminaPrenotazioni = archivio.eliminaPrenotazione(p);
                            
                            obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(eliminaPrenotazioni);

                            obOs.flush();


                        }

                        if (p.getCodicePrenotazione().equals("alive") && p.getNomeCliente().equals("") && p.getPostoPrenotato() == 100) {
                            // istruzione per testare se la connessione è attiva

                            p = new Prenotazione(
                                    "isalive", //codice preno 
                                    "ok", //nome cliente
                                    100, //posti prenotato
                                    "",//t.getNomeTreno(), //nome treno
                                    "", //codice treno
                                    "",//t.getStazionePartenza(), //stazione partenza
                                    "",//t.getStazioneArrivo(), //stazione arrivo
                                    null,//t.getDataPartenza(), //data
                                    0, //posti totali
                                    0, //posti dispo
                                    true);
                            obOs = new ObjectOutputStream(socket.getOutputStream());
                            obOs.writeObject(p);

                            obOs.flush();

                        }






                    }

                    if (p == null) {
                        // disconnessione di un client
                        run = false;

                    }

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TrainServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            jt.append("Ho ricevuto una chiamata di chiusura da: " + utente + "\n");
            jt.append(socket + "\n");
            // chiusura socket
            socket.close();

        } catch (IOException e) {
            jt.append("Socket Closed.\n");
        }
    }
}
