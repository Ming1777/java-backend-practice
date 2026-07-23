package day8;

public class Manager extends Employee{
    public Manager(String name,double salary) {
        super(name, salary);
}
        @Override
        public void work() {
            super.work();
            System.out.println(getName() + "正在制定项目计划");
        }
        public void manageTeam () {
            System.out.println(getName() + "正在管理团队");


        }
    }