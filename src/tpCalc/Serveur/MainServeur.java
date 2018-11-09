package tpCalc.Serveur;

import java.net.ServerSocket;

public class MainServeur {
    public static void main(String[] argsServ) {

        System.out.println("hello world Serveur");

        try {
            ServerSocket socketserver = new ServerSocket(1995);

            RunServerGod serverGod = new RunServerGod(socketserver);
            System.out.println("serveur lancé sur le port : " + socketserver.getLocalPort());



            Thread t = new Thread(serverGod);

            t.start();




        }catch (Exception e) {

            e.printStackTrace();

        }
    }
}
