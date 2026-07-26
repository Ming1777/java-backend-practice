package day10;

public class ExceptionDemo {
    public static void main(String[] args) {
        try {
            int result = 10 / 0;
        } catch (Exception e) {
            System.out.println("不能除以0");;
            e.printStackTrace();
        }

    }
}
