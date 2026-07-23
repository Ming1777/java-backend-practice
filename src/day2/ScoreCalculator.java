package day2;
import java.util.Scanner;

    public class ScoreCalculator {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("请输入Java成绩：");
            int javaScore = scanner.nextInt();


            System.out.print("请输入MySQL成绩：");
            int mysqlScore = scanner.nextInt();

            System.out.print("请输入Linux成绩：");
            int linuxScore = scanner.nextInt();

            int total = javaScore + mysqlScore + linuxScore;

            double average = total / 3.0;

            System.out.println("总分：" + total);
            System.out.println("平均分：" + average);

            scanner.close();
        }
    }
