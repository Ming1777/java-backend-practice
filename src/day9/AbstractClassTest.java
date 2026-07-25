package day9;

public class AbstractClassTest {
    public static void main(String[] args) {
        Employee [] employees = {
                new Developer("小明" ,8000),
                new Tester("小红", 6000)
        };

        for (Employee employee : employees){
            employee.showInfo();
            employee.work();
            System.out.println("-------------------");
        }
    }
}