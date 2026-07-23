package day8;

public class PolymorphismTest {
    public static void main(String[] args) {
        Employee[] employees = {
                new Developer("小明", 8000),
                new Manager("张经理", 15000),
                new Tester("小红", 7500)
        };

        for (Employee employee : employees) {
            employee.work();

            if (employee instanceof Developer developer) {
                developer.writeCode();
            } else if (employee instanceof Manager manager) {
                manager.manageTeam();
            } else if (employee instanceof Tester tester) {
                tester.testSystem();
            }

            System.out.println("----------");
        }
    }
}