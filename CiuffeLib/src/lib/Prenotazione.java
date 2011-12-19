package lib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author albyreturns
 */
import java.io.*;

public class Prenotazione extends Treno implements Serializable {

    private String codicePrenotazione;
    private String nome;
    private int postoPrenotato;
    private boolean confermata;


    public Prenotazione(String cP, String n, int pP, String nT, String c, String sP,
            String sA, Data dP, int pT, int pD, boolean conf) {

        super(nT, c, sP, sA, dP, pT, pD);       


        codicePrenotazione = cP;
        nome = n;
        postoPrenotato = pP;
        confermata = conf;

    }

    public Prenotazione(String sP, String sA, Data d) {      
        super(null, null, sP, sA, d, 0, 0);




    }
    public boolean getConfermata(){
        return confermata;
        
    }
    public void conferma(){
        confermata = true;
    }
    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }
    
    public void setNomeCliente(String n){
        nome = n;
    }

    public String getNomeCliente() {
        return nome;
    }
    
    public void setPostoPrenotato(int p){
        postoPrenotato=p;
        
    }

    public int getPostoPrenotato() {
        return postoPrenotato;
    }
    
    @Override
    public String getCodiceTreno(){
        return super.getCodiceTreno();
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
    public void setPostiDisponibili(int n){
        super.setPostiDisponibili(n);
    }
    @Override
    public int getPostiTotali(){
        return super.getPostiTotali();
    }
    @Override
    public int getPostiDisponibili(){
        return super.getPostiDisponibili();
    }
    
}
