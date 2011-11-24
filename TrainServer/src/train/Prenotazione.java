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

public class Prenotazione extends Treno implements Serializable {

    private String codicePrenotazione;
    private String nome;
    private int postoPrenotato;

    public Prenotazione(String cP, String n, int pP, String nT, String c, String sP,
            String sA, Data dP, int pT, int pD) {

        super(nT, c, sP, sA, dP, pT, pD);       // usato per leggere dall'archivio


        codicePrenotazione = cP;
        nome = n;
        postoPrenotato = pP;

    }

    public Prenotazione(String sP, String sA, Data d) {       // utilizzato per trovare un treno
        super(null, null, sP, sA, d, 0, 0);




    }

    public Prenotazione(String cP, String cT, int pP, String n) throws FileNotFoundException {
        super(cT, null, null, null, null, 0, 0);

        // utilizzato per prenotare prima bisogna eseguire il codice pre generare un numero di prenotazione
        codicePrenotazione = cP;
        postoPrenotato = pP;
        nome = n;
    }
    public Prenotazione(String cP){
        super(null,null,null,null,null,0,0);   // utilizzato per disdire la prenotazione
        codicePrenotazione = cP;
        
    }
    
    
    
    

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public String getNomeCliente() {
        return nome;
    }

    public int getPostoPrenotato() {
        return postoPrenotato;
    }
    @Override
    public String getStazionePartenza(){
        return super.getStazionePartenza();
    }
    @Override
    public String getStazioneArrivo(){
        return super.getStazioneArrivo();
    }
    @Override
    public Data getDataPartenza(){
        return super.getDataPartenza();
    }
    @Override
    public int getPostiTotali(){
        return super.getPostiTotali();
    }
    @Override
    public int getPostiDisponibili(){
        return super.getPostiDisponibili();
    }
    
    

    public void stampaPrenotazione() {
        System.out.println("Codice Prenotazione: " + codicePrenotazione);
        System.out.println("Codice Treno: " + getCodiceTreno());
        System.out.println("Nome: " + nome);
        System.out.println("Posto Prenotato: " + postoPrenotato);
        System.out.println("Posti Totali: " + getPostiTotali());
        System.out.println("Posti Disponibili: " + getPostiDisponibili());

    }
}
