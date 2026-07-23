package day8;

public class TypeConversionTest {
    public static void main(String[] args) {
        Employee employee = new Developer("小明", 8000);

        employee.work();

        if (employee instanceof Developer) {
            Developer developer = (Developer) employee;
            developer.writeCode();
        }
    }
}