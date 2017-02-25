package rpc.test;

import java.io.Serializable;

/**
 * Created by kfzx-liuyz1 on 2017/2/23.
 */
public class User implements Serializable {
    private String firstName;
    private String secondName;

    public User(String secondName, String firstName) {
        this.secondName = secondName;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String toString(){
        return this.firstName + " " + this.secondName;
    }
}
