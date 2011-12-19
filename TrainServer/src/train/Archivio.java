/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package train;

/**
 *
 * @author albyreturns
 */
import java.io.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.*;

public class Archivio {

    private ArrayList<Treno> archivioTreni; // arraylist dei treni
    private ArrayList<Prenotazione> archivioPrenotazioni; // arraylist delle prenotazioni
    private FileReader archivio; // = new FileReader("archivio/Archivio.txt");
    private FileReader prenotazioni; // = new FileReader("archivio/Prenotazioni.txt");
    private FileOutputStream archivioW;  // stream per la scrittura sul file
    private FileOutputStream prenoW;   // stream per la scrittura sul file
    String arch = "";  
    String pren = "";
    boolean treno = false;    // verifica la riuscita della creazione dell'archivio dei treni
    boolean prenotazione = false;  // verifica la riuscita della creazione dell'archivio delle prenotazioni
    int letture;   // numero di volte che viene aperta l'archivio di prenotazione

    public Archivio(String a, String p) throws FileNotFoundException {

        archivio = new FileReader(a);
        prenotazioni = new FileReader(p);
        archivioTreni = new ArrayList();
        archivioPrenotazioni = new ArrayList();
        letture = 0;
    }

    public void creaArchivioTreni() throws FileNotFoundException, NoSuchElementException {
        try {
            String nomeTreno;
            String codiceTreno;
            String stazionePartenza;
            String stazioneArrivo;
            String data;
            int postiTotali = 0;
            int postiDisponibili = 0;
            String temp;

            BufferedReader archivioL = new BufferedReader(archivio);
            temp = archivioL.readLine();

            // scorrimento del database per popolare l'arraylist di treni 
            if (temp.equals("DATABASE TRENI")) {


                while (!"FINE DATABASE".equals(temp)) {


                    temp = archivioL.readLine();
                    if (temp.equals("TRENO") && !"FINE DATABASE".equals(temp)) {

                        nomeTreno = archivioL.readLine();
                        codiceTreno = archivioL.readLine();
                        stazionePartenza = archivioL.readLine();
                        stazioneArrivo = archivioL.readLine();
                        data = archivioL.readLine();
                        Data dataConvertita = new Data();
                        dataConvertita.leggiStringa(data);
                        postiTotali = Data.convertiStringa(archivioL.readLine());
                        postiDisponibili = Data.convertiStringa(archivioL.readLine());

                        Treno t = new Treno(nomeTreno, codiceTreno, stazionePartenza,
                                stazioneArrivo, dataConvertita, postiTotali, postiDisponibili);
                        archivioTreni.add(t);

                    }
                }

                archivioL.close();
                treno = true;

            }
        } catch (IOException ex) {
            Logger.getLogger(Archivio.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void creaArchvioPrenotazioni() throws FileNotFoundException, NoSuchElementException {
        try {





            String codicePrenotazione;
            String codiceTreno;
            String nome;
            int postoPrenotato;
            String confermato;
            boolean confermatoB;


            String temp;
            Prenotazione p;

            BufferedReader archivioL = new BufferedReader(prenotazioni);
            temp = archivioL.readLine();

            // scorrimento del database per popolare l'arraylist di prenotazioni
            if (temp.equals("DATABASE PRENOTAZIONI")) {
                temp = archivioL.readLine();
                temp = archivioL.readLine();
                letture = Data.convertiStringa(temp);

                while (!"FINE DATABASE".equals(temp)) {
                    temp = archivioL.readLine();

                    if (!"FINE DATABASE".equals(temp) && temp.equals("PRENOTAZIONE")) {


                        codicePrenotazione = archivioL.readLine();
                        codiceTreno = archivioL.readLine();
                        nome = archivioL.readLine();
                        postoPrenotato = Data.convertiStringa(archivioL.readLine());
                        confermato = archivioL.readLine();
                        if (confermato.equals("TRUE")) {
                            confermatoB = true;
                        } else {
                            confermatoB = false;
                        }
                        Treno t = getTreno(codiceTreno);

                        p = new Prenotazione(codicePrenotazione, nome, postoPrenotato,
                                t.getNomeTreno(), codiceTreno, t.getStazionePartenza(),
                                t.getStazioneArrivo(), t.getDataPartenza(),
                                t.getPostiTotali(), t.getPostiDisponibili(), confermatoB);

                        if (confermatoB == true || letture % 3 != 0) {
                            archivioPrenotazioni.add(p);
                        } else {
                            decrementaPosto(t,-1);
                        
                        
                        }


                    }
                }
                archivioL.close();
                prenotazione = true;

            }
        } catch (IOException ex) {
            Logger.getLogger(Archivio.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void incrementaLetture() {
        letture++;
    }

    public boolean isArchivioTreno() {
        return treno;
    }

    public boolean isArchioPrenotazione() {
        return prenotazione;
    }

    public ArrayList<Treno> getArchivioTreni() {
        return archivioTreni;
    }

    public ArrayList<Prenotazione> getArchivioPrenotazioni() {
        return archivioPrenotazioni;
    }

    
    // genera un codice di prenotazione unico poiché partendo da 1000
        // scorre tutte le prenotazioni presenti fino a trovare un valore valido
    public String generaCodicePrenotazione() {

        int start = 1000;
        boolean check = true;
        boolean check2 = true;
        String codice = "";

        while (check) {
            check2 = true;


            codice = start + "";
            for (int j = 0; j < archivioPrenotazioni.size() && check2; j++) {
                if (archivioPrenotazioni.get(j).getCodicePrenotazione().equals(codice)) {
                    start++;

                    check2 = false;
                }

            }
            if (check2 == true) {
                check = false;
            }
        }
        return codice;
    }

    public Treno getTreno(String id) {
        Treno t = null;
        boolean trovato = false;
        for (int i = 0; i < archivioTreni.size() && trovato == false; i++) {
            if (archivioTreni.get(i).getCodiceTreno().equals(id)) {
                t = archivioTreni.get(i);
                trovato = true;

            }
        }

        return t;

    }
    
    
// Prenotazione di una quantità di posti
    
    public synchronized ArrayList<Prenotazione> prenotaMultipla(int posti, Prenotazione p, Treno t) {
        ArrayList<Prenotazione> out = new ArrayList();
        String codicePrenotazione = generaCodicePrenotazione();
        int posto = 0;

        for (int i = 0; i < posti; i++) {
            posto = verificaDisponibilitàPosto(t);

            int pDis = t.getPostiDisponibili() - posti;
            Prenotazione p2 = new Prenotazione(codicePrenotazione, p.getNomeCliente(), posto, t.getNomeTreno(), t.getCodiceTreno(), t.getStazionePartenza(), t.getStazioneArrivo(), t.getDataPartenza(), t.getPostiTotali(), pDis, false);

            out.add(p2);
            archivioPrenotazioni.add(p2);

        }
        decrementaPosto(t, posti);


        return out;
    }

    // finalizzazione delle prenotazioni
    public void finalizza(String codice) {
        for (int i = 0; i < archivioPrenotazioni.size(); i++) {
            if (archivioPrenotazioni.get(i).getCodicePrenotazione().equals(codice)) {
                archivioPrenotazioni.get(i).conferma();
            }


        }


    }
// decrementa i posti presenti in un treno a seguito di una prenotazione
    public synchronized void decrementaPosto(Treno t, int posti) {
        for (int i = 0; i < archivioTreni.size(); i++) {
            if (archivioTreni.get(i).getCodiceTreno().equals(t.getCodiceTreno())) {
                int temp = archivioTreni.get(i).getPostiDisponibili();
                archivioTreni.get(i).setPostiDisponibili(temp - posti);

            }
        }


    }
// crea un'arraylist contenente le prenotazioni desiderate
    public ArrayList<Prenotazione> visualizzaPrenotazione(Prenotazione p) {

        ArrayList<Prenotazione> out = new ArrayList();
        for (int i = 0; i < archivioPrenotazioni.size(); i++) {
            if (archivioPrenotazioni.get(i).getCodicePrenotazione().equals(p.getCodicePrenotazione())) {
                out.add(archivioPrenotazioni.get(i));
            }


        }

        return out;

    }
    
    // elimina una prenotazione e la ritorna al cliente per la visualizzazione
    public synchronized ArrayList<Prenotazione> eliminaPrenotazione(Prenotazione p) {

        String codiceTreno = "";
        int postiDisponibili = 0;
        ArrayList<Prenotazione> out = new ArrayList();


        for (int i = 0; i < archivioPrenotazioni.size();) {
            if (archivioPrenotazioni.get(i).getCodicePrenotazione().equals(p.getCodicePrenotazione())) {
                codiceTreno = archivioPrenotazioni.get(i).getCodiceTreno();

                out.add(archivioPrenotazioni.get(i));


                archivioPrenotazioni.remove(i);

                postiDisponibili++;


            } else {
                i++;
            }

        }




        for (int i = 0; i < archivioTreni.size(); i++) {
            if (archivioTreni.get(i).getCodiceTreno().equals(codiceTreno)) {
                int temp = archivioTreni.get(i).getPostiDisponibili();
                postiDisponibili = temp + postiDisponibili;
                archivioTreni.get(i).setPostiDisponibili(postiDisponibili);

            }


        }

        return out;

    }

    // ritorna un'arraylist contenente tutti i treni per una determinata tratta ad un determinato orario e con
    // un determinato numero di posti disponibili
    public synchronized ArrayList<Treno> getArrayListTratta(String partenza, String arrivo, Data data, int posti) {


        ArrayList<Treno> out = new ArrayList();

        for (int i = 0; i < archivioTreni.size(); i++) {



            if (archivioTreni.get(i).getStazionePartenza().equalsIgnoreCase(partenza)) {

                if (archivioTreni.get(i).getStazioneArrivo().equalsIgnoreCase(arrivo)) {

                    if (archivioTreni.get(i).getDataPartenza().getGiorno().equalsIgnoreCase(data.getGiorno())) {

                        if (archivioTreni.get(i).getDataPartenza().getMese().equalsIgnoreCase(data.getMese())) {

                            if (archivioTreni.get(i).getDataPartenza().getAnno().equalsIgnoreCase(data.getAnno())) {

                                if (Data.convertiStringa(archivioTreni.get(i).getDataPartenza().getOra()) == Data.convertiStringa(data.getOra())) {

                                    if (Data.convertiStringa(archivioTreni.get(i).getDataPartenza().getMinuti()) >= Data.convertiStringa(data.getMinuti())) {

                                        if (archivioTreni.get(i).getPostiDisponibili() >= posti && archivioTreni.get(i).getPostiDisponibili() > 0) {

                                            out.add(archivioTreni.get(i));

                                        }
                                    }
                                } else if (Data.convertiStringa(archivioTreni.get(i).getDataPartenza().getOra()) > Data.convertiStringa(data.getOra())) {
                                    if (archivioTreni.get(i).getPostiDisponibili() >= posti && archivioTreni.get(i).getPostiDisponibili() > 0) {

                                        out.add(archivioTreni.get(i));

                                    }
                                }
                            }
                        }
                    }
                }

            }


        }
        return out;

    }
// ritorna una prenotazione
    public ArrayList<Prenotazione> getPrenotazione(String codice) {
        Prenotazione p = null;
        ArrayList<Prenotazione> listaPrenotazione = new ArrayList();
        for (int i = 0; i < archivioPrenotazioni.size(); i++) {
            if (archivioPrenotazioni.get(i).getCodicePrenotazione().equals(codice)) {
                p = archivioPrenotazioni.get(i);
                listaPrenotazione.add(p);
            }

        }
        return listaPrenotazione;

    }

    // verifica se una determinata quantità di posti sono disponibili su un treno
    public synchronized boolean verificaDisponibilitàPosti(Treno t, int posti) {
        if (t.getPostiDisponibili() < posti) {
            return false;
        }
        return true;
    }
 
    // verifica la disponibilità di un determinato posto
    public synchronized int verificaDisponibilitàPosto(Treno t) {   


        int posto = 1;

        String codice = t.getCodiceTreno();

        for (int i = 0; i < archivioPrenotazioni.size(); i++) {
            if (archivioPrenotazioni.get(i).getCodiceTreno().equalsIgnoreCase(codice)
                    && archivioPrenotazioni.get(i).getPostoPrenotato() == posto) {

                posto++;

            }
        }
        return posto;

    }

    
   
// crea il path assoluto per l'archivio dei treni
    protected static FileReader createDB(String path) throws FileNotFoundException {
        java.net.URL imgURL = Archivio.class.getResource(path);
        if (imgURL != null) {

            return new FileReader(imgURL.getPath());
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
// crea il path assoluto per l'archivio delle prenotazioni
    protected static FileOutputStream createDBW(String path) throws FileNotFoundException {
        java.net.URL imgURL = Archivio.class.getResource(path);
        if (imgURL != null) {

            return new FileOutputStream(imgURL.getPath());
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    // stampa su file entrambi gli archivi
    public void stampaSuFile() throws FileNotFoundException {
        archivioW = new FileOutputStream(arch);
        PrintStream scrivi = new PrintStream(archivioW);
        scrivi.println("DATABASE TRENI");
        for (int i = 0; i < archivioTreni.size(); i++) {
            scrivi.println("TRENO");
            scrivi.println(archivioTreni.get(i).getNomeTreno());
            scrivi.println(archivioTreni.get(i).getCodiceTreno());
            scrivi.println(archivioTreni.get(i).getStazionePartenza());
            scrivi.println(archivioTreni.get(i).getStazioneArrivo());
            scrivi.print(archivioTreni.get(i).getDataPartenza().getGiorno() + "/");
            scrivi.print(archivioTreni.get(i).getDataPartenza().getMese() + "/");
            scrivi.print(archivioTreni.get(i).getDataPartenza().getAnno() + "-");
            scrivi.print(archivioTreni.get(i).getDataPartenza().getOra() + ":");
            scrivi.print(archivioTreni.get(i).getDataPartenza().getMinuti() + "\n");
            scrivi.println(archivioTreni.get(i).getPostiTotali());
            scrivi.println(archivioTreni.get(i).getPostiDisponibili());

        }
        scrivi.println("FINE DATABASE");

        prenoW = new FileOutputStream(pren);

        scrivi = null;
        scrivi = new PrintStream(prenoW);
        scrivi.println("DATABASE PRENOTAZIONI");
        incrementaLetture();
        scrivi.println("NUMERO LETTURE");
        scrivi.println(letture);
        for (int i = 0; i < archivioPrenotazioni.size(); i++) {
            scrivi.println("PRENOTAZIONE");

            scrivi.println(archivioPrenotazioni.get(i).getCodicePrenotazione());
            scrivi.println(archivioPrenotazioni.get(i).getCodiceTreno());
            scrivi.println(archivioPrenotazioni.get(i).getNomeCliente());
            scrivi.println(archivioPrenotazioni.get(i).getPostoPrenotato());
            if (archivioPrenotazioni.get(i).getConfermata()) {
                scrivi.println("TRUE");
            } else {
                scrivi.println("FALSE");
            }


        }
        scrivi.println("FINE DATABASE");

    }

    public boolean setPath(String arch, String pren) {
        try {
            archivio = new FileReader(arch);
            prenotazioni = new FileReader(pren);
            this.arch = arch;
            this.pren = pren;
           
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        }
    }
}
