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

public class Archivio {

    private ArrayList<Treno> archivioTreni;
    private ArrayList<Prenotazione> archivioPrenotazioni;

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

            FileReader archivio = new FileReader("/Users/albyreturns/Desktop/Archivio.txt");
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

            FileReader archivio = new FileReader("/Users/albyreturns/Desktop/Archivio2.txt");
            BufferedReader archivioL = new BufferedReader(archivio);
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

    public ArrayList<Treno> getArrayListTratta(String partenza, String arrivo, Data data) {


        ArrayList<Treno> out = new ArrayList();

        for (int i = 0; i < archivioTreni.size(); i++) {
            if (archivioTreni.get(i).getStazionePartenza().equalsIgnoreCase(partenza)
                && archivioTreni.get(i).getStazioneArrivo().equalsIgnoreCase(arrivo)
                && archivioTreni.get(i).getDataPartenza().getGiorno().equalsIgnoreCase(data.getGiorno())
                && archivioTreni.get(i).getDataPartenza().getMese().equalsIgnoreCase(data.getMese())
                && archivioTreni.get(i).getDataPartenza().getAnno().equalsIgnoreCase(data.getAnno())
                && Data.convertiStringa(archivioTreni.get(i).getDataPartenza().getOra())>= Data.convertiStringa(data.getOra())
                && Data.convertiStringa(archivioTreni.get(i).getDataPartenza().getMinuti())>=Data.convertiStringa(data.getMinuti())
                ) { 
                    out.add(archivioTreni.get(i));
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

    public boolean verificaDisponibilitàPosto(Treno t, int posto) {   // ritorna true se è disponibile

        boolean trovato = false;
        String codice = t.getCodiceTreno();
        for (int i = 0; i < archivioPrenotazioni.size() && trovato == false; i++) {
            if (archivioPrenotazioni.get(i).getCodiceTreno().equalsIgnoreCase(codice)
                    && archivioPrenotazioni.get(i).getPostoPrenotato() != posto) {
                trovato = true;
            }
        }
        return trovato;
    }
}
