package day22;

import java.util.HashSet;

public class UserSetPractice0917 {

    record User(int id) {}

    public static void main(String[] args) {
        HashSet<User> users = new HashSet<>();

        users.add(new User(1));
        users.add(new User(1));

        System.out.println(users.size()); // 1
    }
}