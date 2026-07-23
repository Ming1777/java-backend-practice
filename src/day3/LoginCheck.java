package day3;

import java.util.Scanner;

public class LoginCheck {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入用户名：");
        String username = scanner.next();

        System.out.print("请输入密码：");
        String password = scanner.next();

        if ( username.equals("admin") && password.equals("123456") ) {
            System.out.println("登录成功");
        } else {
            System.out.println("用户名或密码错误");
        }

        scanner.close();
    }
}