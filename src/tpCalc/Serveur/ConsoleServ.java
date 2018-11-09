package tpCalc.Serveur;

import com.sun.source.tree.SynchronizedTree;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleServ implements Runnable {

    public boolean isServerOn;

    private Scanner scConsole;
    private ServerSocket socketserver;
    private ArrayList<ObjectOutputStream> ClientsOuptutList;

    public ConsoleServ(ServerSocket s, ArrayList<ObjectOutputStream> theList) {
        scConsole = new Scanner(System.in);
        socketserver = s;
        isServerOn = true;
        ClientsOuptutList = theList;
    }



    @Override
    public void run() {
        System.out.println("hello here is the consol : type Help for the list of command?");
        try {
            while(isServerOn){
                lunchConsol();
            }

            for(ObjectOutputStream oos : ClientsOuptutList){
                oos.writeObject("exitClient");
                oos.close();
            }

            scConsole.close();
            socketserver.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lunchConsol() {
        System.out.println("server>");
        String commande = scConsole.next();

        switch (commande){
            case("exit"):
                isServerOn=false;
                break;
            case("lol"):
                System.out.println("hmmmmmm");
                break;
            case("nbClient"):
                System.out.println("Nb Clients : "+nbClient());
                break;
            default:
                break;
        }
    }

    private synchronized int nbClient(){
        return ClientsOuptutList.size();
    }


}
