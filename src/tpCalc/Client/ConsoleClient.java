package tpCalc.Client;

import tpCalc.Shared.User;

import java.io.*;
import java.util.Scanner;

public class ConsoleClient implements Runnable {

    public BufferedReader sc;
    private ObjectOutputStream oos;
    private User user;

    public ConsoleClient (ObjectOutputStream out, User user){
        sc= new BufferedReader(new InputStreamReader(System.in));
        oos = out;
        this.user = user;
    }

    @Override
    public void run() {
        String commandUser;
        try {
            System.out.println("start communicate:");
            do {
                commandUser = sc.readLine();//attend une commande
                oos.writeObject(commandUser);

            } while (!commandUser.equals("exit"));
            System.out.println("bye " +user.getUsername());
        }catch (IOException e){
            System.out.println("catch Console Client");
            e.printStackTrace();
        }

    }
}
