package day12;

import java.util.Map;
import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args) {
        HashMap <Integer,String> users = new HashMap<>();

        users.put(1, "小明");
        users.put(2, "小红");
        users.put(3, "小刚");
        users.put(4, "小红");

        System.out.println(users);
        System.out.println("编号2的用户：" + users.get(2));
        System.out.println("用户数量：" + users.size());
        System.out.println("是否存在编号1：" + users.containsKey(2));
        System.out.println("是否存在小刚：" + users.containsValue("小红"));

        System.out.println("----------------------------------");
        for (Integer id : users.keySet()){
            String name = users.get(id);

            System.out.println("编号：" + id + "，姓名：" + name);
        }
        System.out.println("-------------------------------");
        for (Map.Entry<Integer, String> entry : users.entrySet()) {

            Integer id = entry.getKey();
            String name = entry.getValue();

            System.out.println("编号：" + id + "，姓名：" + name);
        }

        System.out.println("----------------");




    }
}
