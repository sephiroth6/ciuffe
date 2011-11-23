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
public class Treno implements Serializable {

    private String nomeTreno;
    private String codiceTreno;
    private String stazionePartenza;
    private String stazioneArrivo;
    private Data dataPartenza; // data ed ora di partenza
    private int postiTotali;
    private int postiDisponibili;

    public Treno(String n, String c, String sP, String sA, Data dP, int pT, int pD) {
        nomeTreno = n;
        codiceTreno = c;
        stazionePartenza = sP;
        stazioneArrivo = sA;
        dataPartenza = dP;
        postiTotali = pT;
        postiDisponibili = pD;
    }

    public String getNomeTreno() {
        return nomeTreno;

    }

    public String getCodiceTreno() {
        return codiceTreno;

    }

    public String getStazionePartenza() {
        return stazionePartenza;
    }

    public String getStazioneArrivo() {
        return stazioneArrivo;
    }

    public Data getDataPartenza() {
        return dataPartenza;
    }

    public int getPostiTotali() {
        return postiTotali;

    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void stampaInfoTreno() {
        if (nomeTreno != null) {
            System.out.println("Nome Treno: " + nomeTreno);
            System.out.println("Codice Treno: " + codiceTreno);
            System.out.println("Tratta: " + stazionePartenza + " - " + stazioneArrivo);
            System.out.print("Data Partenza: ");
            dataPartenza.stampaData();
            System.out.println("Posti Totali: " + postiTotali);
            System.out.println("Posti Disponibili: " + postiDisponibili);
        } else {
            System.out.println("Treno non trovato");
        }


    }
}
