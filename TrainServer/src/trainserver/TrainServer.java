package trainserver;

//Importo i package
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//Creazione di una classe per il Multrithreading
public class TrainServer extends Thread implements Runnable{

    private Socket socket;
    //private ServerSocket ss;

    public TrainServer(Socket socket) {
        this.socket = socket;
      //  this.ss=ss;
    }

    //esecuzione del Thread sul Socket
    @Override
    public void run() {
        try {
            DataInputStream is = new DataInputStream(socket.getInputStream());
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String userInput = is.readUTF();
           //     ObjectInputStream objectInputStream =
             //           new ObjectInputStream(socket.getInputStream());
            //    Prenotazione p = (Prenotazione)objectInputStream.readObject();
                if (userInput == null || userInput.equals("exit")) {
                    break;
                }
                os.writeUTF(userInput + '\n');
                System.out.println("Il Client ha scritto: " + userInput);
              //  p.stampaPrenotazione();
            }
            os.close();
            is.close();
            System.out.println("Ho ricevuto una chiamata di chiusura da:\n"
                    + socket + "\n");
            socket.close();
           // ss.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
}