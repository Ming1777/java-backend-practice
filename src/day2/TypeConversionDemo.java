package day2;

public class TypeConversionDemo {
    public static void main(String[] args) {
        System.out.println(7 / 2);
        System.out.println(7.0 / 2);
        System.out.println((double) 7 / 2);

        int javaScore = 80;
        int mysqlScore = 86;
        int linuxScore = 90;
        double average1 = (javaScore + mysqlScore + linuxScore) / 3;
        double average2 = (javaScore + mysqlScore + linuxScore) / 3.0;


        System.out.println("错误或不精确的平均分：" + average1);
        System.out.println("正确平均分：" + average2);
    }
}