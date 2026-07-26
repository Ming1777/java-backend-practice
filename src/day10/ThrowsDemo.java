package day10;

public class ThrowsDemo {
    public static void main(String[] args) {
        try {
            checkAge(-5);
        } catch (Exception e) {
            System.out.println("年龄检查失败：" + e.getMessage());
        }
        System.out.println("程序继续运行");
    }

    public static void checkAge(int age) throws Exception{
        if (age < 0){
            throw new Exception("年纪不能小于0");
        }
        System.out.println("年龄合法" + age);
    }
}
