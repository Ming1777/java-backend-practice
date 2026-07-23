package day7;

public class InheritanceTest {
    public static void main(String[] args) {
        Developer developer = new Developer();

        developer.setName("小明");
        developer.setSalary(8000);

        developer.work();
        developer.writeCode();

        System.out.println("工资：" + developer.getSalary());
    }
}