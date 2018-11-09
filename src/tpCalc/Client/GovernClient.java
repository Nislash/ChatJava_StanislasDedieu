package tpCalc.Client;

import tpCalc.Shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GovernClient {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ConsoleClient console;
    private Socket socketNo1;
    private User user;

    public GovernClient() throws IOException {
        socketNo1 = new Socket("",1995);
        user = new User("null","null");
        in = new ObjectInputStream(socketNo1.getInputStream());
        out = new ObjectOutputStream(socketNo1.getOutputStream());

        console = new ConsoleClient(out,user); //runnable to be run in the thread and seend message anytime
    }

    public void Connexion() throws IOException, ClassNotFoundException {

        //set check user routine
        out.writeObject("--455513325--checkUserForConnexion");

        System.out.println("what is your username");
        user.setUsername(console.sc.readLine());
        out.writeObject(user.getUsername()); //we send the username
        //the server will check if it exist

        System.out.println((String)in.readObject());
        String answer =(String) in.readObject(); //2nd Outputstream send from the server
        switch (answer){
            case("password"):
                String thePasswordToFind=(String)in.readObject();
                String theUserTry;
                do{
                    System.out.println("tape your password or exit");
                    theUserTry = console.sc.readLine();
                }while (!theUserTry.equals(thePasswordToFind) && !theUserTry.equals("exit"));

                if(theUserTry.equals("exit"))
                    out.writeObject("exit");//what a buetifful system.. i seend exit.. and it leave
                else{
                    out.writeObject("--5864512345--getUser");
                    out.writeObject(user.getUsername());
                    user=(User)in.readObject(); //we take back the user from the serv
                }
                break;
            case("create"):
                System.out.println("please create your password");
                user.setNewPassword(console.sc.readLine());
                out.writeObject("--12354811546--addUser");
                out.writeObject(user);
                System.out.println((String)in.readObject());
                break;
            default:
                break;
        }

    }

    public void startRoutine() throws IOException, ClassNotFoundException {
        String Reception;

        Thread t_userConsole = new Thread(console);
        t_userConsole.start();

        do {
            Reception = (String) in.readObject();
            System.out.println(Reception);

        }while (!Reception.equals("exitClient"));


        console.sc=null;

        in.close();
        out.close();
        socketNo1.close();
    }

}
