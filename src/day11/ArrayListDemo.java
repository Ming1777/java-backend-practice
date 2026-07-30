package day11;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListDemo {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();

        names.add("小明");
        names.add("小红");
        names.add("小刚");
        System.out.println(names);
        System.out.println("元素数量：" + names.size());

        String firstname = names.get(0);
        System.out.println("第一个元素： " + firstname);

        names.set(1, "啊啊啊");
        System.out.println("修改后：" + names);

        names.remove(0);
        System.out.println("删除后：" + names);

        boolean contain = names.contains("小明");
        System.out.println("是否包含小明： " + contain);

        int index = names.indexOf("啊啊啊");
        System.out.println("啊啊啊的下标： " + index);


        System.out.println("----------------------------------------------");
        for (String name : names) {
            System.out.println(name);
        }

        for (int i = 0; i < names.size(); i++) {
            System.out.println("下标：" + i + "，姓名：" + names.get(i));
        }
    }
}