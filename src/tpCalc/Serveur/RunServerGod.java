package tpCalc.Serveur;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class RunServerGod implements Runnable {
    private ServerSocket socketserver;
    private ConsoleServ console;
    private ArrayList<ObjectOutputStream> ClientsOutputStreams;


    public RunServerGod(ServerSocket s) {
        socketserver = s;

        ClientsOutputStreams= new ArrayList<>();

        console = new ConsoleServ(s,ClientsOutputStreams); //Start Runnable Console using ofr server
    }


    @Override
    public void run() {

        try {
            Thread t_console = new Thread(console);
            t_console.start();

            while (console.isServerOn) {
                startConnexion();
            }


        } catch (Exception e) {
            System.out.println("Serveur Shut Down");
        }
    }


    private void startConnexion() throws IOException, ClassNotFoundException {
        Socket socketConnexion = socketserver.accept(); // Un client se connecte on l'accepte
        System.out.println("Connexion depuis :" + socketConnexion.getInetAddress());


        ObjectOutputStream outClient = new ObjectOutputStream(socketConnexion.getOutputStream());
        ClientsOutputStreams.add(outClient); //We add the OutputStream to the list. To seend object to all user

        //-------------Handl Client-------------

        Thread t_HandleClient = new Thread(new HandleClient(socketConnexion, console.isServerOn, outClient, ClientsOutputStreams));
        t_HandleClient.start();

    }

    //disconnect etc
}
