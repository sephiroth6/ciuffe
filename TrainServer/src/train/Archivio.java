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
import lib.Prenotazione;

public class Archivio {

    private ArrayList<Treno> archivioTreni;
    private ArrayList<Prenotazione> archivioPrenotazioni;
    private FileReader archivio = createDB("archivio/Archivio.txt");
    private FileReader prenotazioni = createDB("archivio/Prenotazioni.txt");

    public Archivio() throws FileNotFoundException {

        archivioTreni = new ArrayList();
        archivioPrenotazioni = new ArrayList();

    }

    public void creaArchivioTreni() throws FileNotFoundException, /*FormatException,*/ NoSuchElementException {
        try {
            String nomeTreno;
            String codiceTreno;
            String stazionePartenza;
            String stazioneArrivo;
            String data;
            int postiTotali = 0;
            int postiDisponibili = 0;
            String temp;
            //FileReader archivio = new FileReader("/home/angelo/ciuffe/TrainServer/src/train/archivio/Archivio.txt");
            BufferedReader archivioL = new BufferedReader(archivio);
            temp = archivioL.readLine();

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

        } catch (IOException ex) {
            Logger.getLogger(Archivio.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void creaArchvioPrenotazioni() throws FileNotFoundException, /*FormatException,*/ NoSuchElementException {
        try {





            String codicePrenotazione;
            String codiceTreno;
            String nome;
            int postoPrenotato;

            String temp;
            Prenotazione p;

            //FileReader prenotazioni = new FileReader("/home/angelo/ciuffe/TrainServer/src/train/archivio/Prenotazioni.txt");
            BufferedReader archivioL = new BufferedReader(prenotazioni);
            temp = archivioL.readLine();
            while (!"FINE DATABASE".equals(temp)) {
                temp = archivioL.readLine();

                if (!"FINE DATABASE".equals(temp) && temp.equals("PRENOTAZIONE")) {


                    codicePrenotazione = archivioL.readLine();
                    codiceTreno = archivioL.readLine();
                    nome = archivioL.readLine();
                    postoPrenotato = Data.convertiStringa(archivioL.readLine());
                    Treno t = getTreno(codiceTreno);

                    p = new Prenotazione(codicePrenotazione, nome, postoPrenotato,
                            t.getNomeTreno(), codiceTreno, t.getStazionePartenza(),
                            t.getStazioneArrivo(), t.getDataPartenza(),
                            t.getPostiTotali(), t.getPostiDisponibili());
                    archivioPrenotazioni.add(p);


                }
            }
            archivioL.close();
        } catch (IOException ex) {
            Logger.getLogger(Archivio.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public ArrayList<Treno> getArchivioTreni() {
        return archivioTreni;
    }

    public ArrayList<Prenotazione> getArchivioPrenotazioni() {
        return archivioPrenotazioni;
    }

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

    public ArrayList<Prenotazione> prenotaMultipla(int posti, Prenotazione p, Treno t) {
        ArrayList<Prenotazione> out = new ArrayList();
        String codicePrenotazione = generaCodicePrenotazione();
        int posto = 0;

        for (int i = 0; i < posti; i++) {
            // trovare primo posto disponibile
            posto = verificaDisponibilitàPosto(t);

            int pDis = t.getPostiDisponibili() - posti;
            Prenotazione p2 = new Prenotazione(codicePrenotazione, p.getNomeCliente(), posto, t.getNomeTreno(), t.getCodiceTreno(), t.getStazionePartenza(), t.getStazioneArrivo(), t.getDataPartenza(), t.getPostiTotali(), pDis);
            out.add(p2);
            archivioPrenotazioni.add(p2);

        }
        decrementaPosto(t, posti);


//        for (int u = 0; u < archivioPrenotazioni.size(); u++) {
//            archivioPrenotazioni.get(u).stampaPrenotazione();
//        }
        return out;
    }

    public void decrementaPosto(Treno t, int posti) {
        for (int i = 0; i < archivioTreni.size(); i++) {
            if (archivioTreni.get(i).getCodiceTreno().equals(t.getCodiceTreno())) {
                int temp = archivioTreni.get(i).getPostiDisponibili();
                archivioTreni.get(i).setPostiDisponibili(temp - posti);

            }
        }


    }

    public ArrayList<Prenotazione> visualizzaPrenotazione(Prenotazione p) {

        ArrayList<Prenotazione> out = new ArrayList();
        for (int i = 0; i < archivioPrenotazioni.size(); i++) {
            if (archivioPrenotazioni.get(i).getCodicePrenotazione().equals(p.getCodicePrenotazione())) {
                out.add(archivioPrenotazioni.get(i));
            }


        }

        return out;

    }

    public ArrayList<Prenotazione> eliminaPrenotazione(Prenotazione p) {

        String codiceTreno = "";
        int postiDisponibili = 0;
        ArrayList<Prenotazione> out = new ArrayList();


        for (int i = 0; i < archivioPrenotazioni.size(); i++) {
            if (archivioPrenotazioni.get(i).getCodicePrenotazione().equals(p.getCodicePrenotazione())) {
                codiceTreno = archivioPrenotazioni.get(i).getCodiceTreno();
                out.add(archivioPrenotazioni.get(i));

//ciao
                //asd
                archivioPrenotazioni.remove(i);
                archivioPrenotazioni.trimToSize();
                postiDisponibili++;


            }

        }




        for (int i = 0; i < archivioTreni.size(); i++) {
            if (archivioTreni.get(i).getCodiceTreno().equals(codiceTreno)) {
                postiDisponibili = archivioTreni.get(i).getPostiDisponibili() + postiDisponibili;
                archivioTreni.get(i).setPostiDisponibili(postiDisponibili);

            }


        }

        return out;

    }

    public ArrayList<Treno> getArrayListTratta(String partenza, String arrivo, Data data, int posti) {


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

    public boolean verificaDisponibilitàPosti(Treno t, int posti) {
        if (t.getPostiDisponibili() < posti) {
            return false;
        }
        return true;
    }

    public int verificaDisponibilitàPosto(Treno t) {   // ritorna true se è disponibile


        int posto = 1;

        String codice = t.getCodiceTreno();

        for (int i = 0; i < archivioPrenotazioni.size(); i++) {
            if (archivioPrenotazioni.get(i).getCodiceTreno().equalsIgnoreCase(codice)
                    && archivioPrenotazioni.get(i).getPostoPrenotato() == posto) {
                //trovato = true;
                posto++;

            }
        }
        return posto;

    }

    public void selezionaOperazione(Prenotazione p) {
        if (p.getCodicePrenotazione().equals("")) {
        } else {
        }



    }

    protected static FileReader createDB(String path) throws FileNotFoundException {
        java.net.URL imgURL = Archivio.class.getResource(path);
        if (imgURL != null) {
            return new FileReader(imgURL.getPath());
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    protected static FileOutputStream createDBW(String path) throws FileNotFoundException {
        java.net.URL imgURL = Archivio.class.getResource(path);
        if (imgURL != null) {
            return new FileOutputStream(imgURL.getPath());
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void stampaSuFile() throws FileNotFoundException {
        FileOutputStream prova = createDBW("archivio/Archivio.txt");
        PrintStream scrivi = new PrintStream(prova);
        scrivi.println("DATABASE");
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
        prova = createDBW("archivio/Prenotazioni.txt");
        scrivi = new PrintStream(prova);
        scrivi.println("DATABASE");

        for (int i = 0; i < archivioPrenotazioni.size(); i++) {
            scrivi.println("PRENOTAZIONE");
            scrivi.println(archivioPrenotazioni.get(i).getCodicePrenotazione());
            scrivi.println(archivioPrenotazioni.get(i).getCodiceTreno());
            scrivi.println(archivioPrenotazioni.get(i).getNomeCliente());
            scrivi.println(archivioPrenotazioni.get(i).getPostoPrenotato());
        }
        scrivi.println("FINE DATABASE");

    }
}
