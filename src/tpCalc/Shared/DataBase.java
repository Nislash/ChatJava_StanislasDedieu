package tpCalc.Shared;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DataBase {
    private File DB;
    private ArrayList<User> userList;

    public DataBase(String DBname) throws IOException, ClassNotFoundException {
        this.DB = new File(DBname);
        this.userList= loadUserList();
    }

    private ArrayList<User> loadUserList() throws IOException, ClassNotFoundException {
        ArrayList<User> dataUsers = new ArrayList<User>();

        if(this.DB.exists() && !this.DB.isDirectory()){

            FileInputStream lectureDeDB = new FileInputStream(DB);
            ObjectInputStream OSuserList = new ObjectInputStream(lectureDeDB);


            dataUsers = (ArrayList<User>)OSuserList.readObject();
            //set all the user list in the userList
            return dataUsers;
        } else{
            dataUsers = new ArrayList<>();
            System.out.println("error while loading userlist.. DB Exist? check path");
            return dataUsers;
        }
    }

    private void addNewUser(User newUserIncoming) throws IOException {
        FileOutputStream FileDBWrite = new FileOutputStream(DB);
        ObjectOutputStream write = new ObjectOutputStream(FileDBWrite);

        userList.add(newUserIncoming);
        write.writeObject(userList);

    }

    public String addNewUserFinal(User newUserIncoming) throws IOException {
        char flag=0;
        int noRandUser;

        do{ //check il userlist to add new unique noUser
            flag = 0;
            noRandUser = ThreadLocalRandom.current().nextInt();
            for(User alluser : userList){
                if(alluser.getNoUser()==noRandUser)
                    flag=1;
            }
        }while(flag==1);


        newUserIncoming.setNoUser(noRandUser);
        addNewUser(newUserIncoming);
        return ("Database update, wellcome "+newUserIncoming.getUsername()+"!");
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

}
