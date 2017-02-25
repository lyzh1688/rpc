package rpc.test;

/**
 * Created by kfzx-liuyz1 on 2017/2/23.
 */
public class TestImpl implements Test {
    public String testStr() {
        return "HELLO WORLD";
    }

    public User testObj(String firstName, String secondName) {
        return new User(firstName,secondName);
    }
}
