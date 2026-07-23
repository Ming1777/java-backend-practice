package day8;

public class InheritanceTest {
    public static void main(String[] args) {
        Developer developer = new Developer("小明", 8000);
        Manager manager = new Manager("张经理", 15000);

        System.out.println("——开发人员——");
        developer.work();
        developer.writeCode();

        System.out.println("——经理——");
        manager.work();
        manager.manageTeam();
    }
}