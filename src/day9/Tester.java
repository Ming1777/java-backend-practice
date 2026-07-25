package day9;

public class Tester extends Employee{
    public Tester(String name,double salary){
        super(name, salary);
    }

    @Override
    public void work() {
        System.out.println(getName() + "正在测试项目");
    }
}
