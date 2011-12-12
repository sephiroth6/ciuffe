/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package train;

/**
 *
 * @author albyreturns
 */
import java.util.*;
import java.io.*;
import java.net.*;
import lib.*;

public class TrainClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        Data d = new Data ("21","12","2010","18","55");
        Prenotazione p;
        Socket clientSocket;	// riferimento al socket 
        DataOutputStream output;
        DataInputStream input;
        ObjectInputStream dalServer;
        ObjectOutputStream versoServer;
        
        boolean t = true;
        try {

            // prova git
            clientSocket = new Socket("", 5001);  // connessione 
            // effettua la comunicazione con il server

            
                
                String name = "Alberto Aloisi";
                //String cP, String n, int pP, String nT, String c, String sP,
            //String sA, Data dP, int pT, int pD
                p = new Prenotazione("",name,0,"","","Milano", "Roma", d,0,0);
                versoServer = new ObjectOutputStream(clientSocket.getOutputStream());
                versoServer.writeObject(p);
                
                
              


            

            clientSocket.close();	// chiude il socket 
        } catch (IOException e) {
        }
    }
}
