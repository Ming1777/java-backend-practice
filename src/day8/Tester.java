package day8;

public class Tester extends Employee {

    public Tester(String name, double salary) {
        super(name, salary);
    }

    @Override
    public void work() {
        super.work();
        System.out.println(getName() + "正在测试项目功能");
    }

    public void testSystem() {
        System.out.println(getName() + "正在执行系统测试");
    }
}