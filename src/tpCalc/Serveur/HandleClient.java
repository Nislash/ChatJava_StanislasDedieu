package tpCalc.Serveur;

import tpCalc.Shared.DataBase;
import tpCalc.Shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class HandleClient implements Runnable {
    private Socket clientSocket;

    private boolean isServerOn;

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ArrayList<ObjectOutputStream> theClientsOutList;
    private DataBase dbUser;
    private User user;

    public HandleClient(Socket s, boolean isServerOn, ObjectOutputStream outObject, ArrayList<ObjectOutputStream> theClientsList) throws IOException, ClassNotFoundException {
        this.clientSocket = s;
        this.isServerOn = isServerOn;
        this.out = outObject;
        this.theClientsOutList = theClientsList;
        this.dbUser = new DataBase("UserDB.db");
    }

    @Override
    public void run() {
        try{

            in = new ObjectInputStream(clientSocket.getInputStream());

            while(isServerOn && clientSocket.isConnected()){//tant que l'utilisateur n'as pas fait la commande pour quitter,
                commandUser();
            }

            //Fin d'handleClient, disconnect
            out.close();
            in.close();
            System.out.println("Client Disconnect");

        }catch (Exception e) {
            System.out.println("Client Disconnect");
        }

    }

    private void commandUser() throws IOException, ClassNotFoundException {

        String command = (String)in.readObject();
        System.out.println(command);

        switch (command){
            case("exit"): //End connection
                theClientsOutList.remove(out); //We remouve the Client from the connected
                out.writeObject("exitClient");
                break;
            case("--455513325--checkUserForConnexion"): //i dont whant that a user call manualy thoses function
                out.writeObject(checkUserForConnexion((String)in.readObject()));
                break;
            case("--5864512345--getUser"):
                out.writeObject(getUserConnexion((String)in.readObject()));
                break;
            case("--12354811546--addUser"):
                this.user=(User)in.readObject();
                out.writeObject(dbUser.addNewUserFinal(user)); //all is in the name
                break;
            default: //the default is when it's just talking on chat
                for (ObjectOutputStream oos : theClientsOutList) {
                    oos.writeObject(user.getUsername()+": "+command);
                    oos.flush();
                }

                break;
        }

    }

    private String checkUserForConnexion(String username) throws IOException {

        for(User user : dbUser.getUserList()){
            if(user.getUsername().equals(username)) {
                out.writeObject("Wellcome Back " + username+" please enter your password");
                out.writeObject("password");
                return user.getPassword();
            }
        }
        out.writeObject("Sorry, no "+username+" find in the DB, please create account by adding password");
        return "create";
    }

    private User getUserConnexion(String userName){
        for(User user : dbUser.getUserList()){
            if(user.getUsername().equals(userName)){
                this.user=user;
                return user;
            }
        }
        //unreachable point
        System.out.println("problem, reach getUserConnexion unreachable Point");
        return null;
    }

}
