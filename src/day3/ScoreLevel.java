package day3;

import java.util.Scanner;

public class ScoreLevel {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入成绩：");
        int score = scanner.nextInt();

        if (score < 0 || score > 100) {
            System.out.println("成绩输入不合法");
        } else if (score >= 90) {
            System.out.println("优秀");
        } else if (score >= 80) {
            System.out.println("良好");
        } else if (score >= 60) {
            System.out.println("及格");
        } else {
            System.out.println("不及格");
        }
        scanner.close();
    }
}