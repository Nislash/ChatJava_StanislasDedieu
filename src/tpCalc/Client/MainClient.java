package tpCalc.Client;

import tpCalc.Shared.User;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] argsClient) {


        try {
            GovernClient mainUser = new GovernClient();
            mainUser.Connexion();
            mainUser.startRoutine();

            System.exit(0);
            System.out.println("bye");

        }catch (Exception e) {

            e.printStackTrace();
            System.out.println("exit MainClient exeption");
        }
    }
}
