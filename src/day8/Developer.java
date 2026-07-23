package day8;

public class Developer extends Employee {

    public Developer(String name, double salary) {
        super(name, salary);
    }

    @Override
    public void work() {
        super.work();
        System.out.println(getName() + "正在开发Java项目");
    }

    public void writeCode() {
        System.out.println(getName() + "正在编写Java代码");
    }
}