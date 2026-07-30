package day11;

import java.util.ArrayList;

public class UserListDemo {
    public static void main(String[] args) {

        ArrayList<User> users = new ArrayList<>();

        users.add(new User(1, "小明", 20));
        users.add(new User(2, "小红", 21));
        users.add(new User(3, "小刚", 22));

        int targetId = 2;
        boolean found = false;

        for (User user : users) {
            if (user.getId() == targetId) {
                System.out.println("找到了用户：" + user);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("没有找到编号为" + targetId + "的用户");
        }

        for (User user : users) {
            System.out.println(
                    "编号：" + user.getId()
                            + "，姓名：" + user.getName()
                            + "，年龄：" + user.getAge()
            );



        }
    }
}