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

public class TrainClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        Data d = new Data ("21","12","2010","18","55");
        Prenotazione p = new Prenotazione("Milano", "Roma", d);
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

            while (t) {
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());
                String choice = in.nextLine();
                 System.out.println("prima dell outputWrite");
                output.writeUTF(choice);
                System.out.println("dopo dell outputWrite");
                //versoServer = new ObjectOutputStream(clientSocket.getOutputStream());
                
                //versoServer.writeObject(p);
                
                String inString = input.readUTF();
                System.out.println("dopo inString");
                
                System.out.println(inString.toUpperCase());
//                if ("exit".equals(choice)) {
//                    t = false;
//                }


            }

            clientSocket.close();	// chiude il socket 
        } catch (IOException e) {
        }
    }
}
