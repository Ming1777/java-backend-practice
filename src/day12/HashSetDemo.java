package day12;

import java.util.HashSet;

public class HashSetDemo {
    public static void main(String[] args) {
        HashSet<String> names = new HashSet<>();

        boolean firstAdd = names.add("小李");
        boolean secondAdd = names.add("小李");

        System.out.println("第一次添加小李：" + firstAdd);
        System.out.println("第二次添加小李：" + secondAdd);

        names.add("1");
        names.add("2");
        names.add("3");
        names.add("4");

        System.out.println(names);
        System.out.println("元素数量："  + names.size());



        HashSet<User> users = new HashSet<>();

        users.add(new User(1, "小明"));
        users.add(new User(1, "小明"));

        System.out.println(users);
        System.out.println(users.size());
    }
}
