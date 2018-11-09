package tpCalc.Shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

    private int noUser;
    private String username;
    private String password;

    public User( String username, String password) {

        this.password = password;
        this.username = username;
    }


    public int getNoUser() {
        return noUser;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setNoUser(int no){
        this.noUser=no;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setNewPassword(String newPassword){
        this.password=newPassword;
    }

    public String toString(){
        return this.getUsername()+" "+this.getNoUser();
    }


}
