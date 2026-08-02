package day15;

public class StaticDemo {
    public static void main(String[] args) {
        Student student1 = new Student("小明");
        Student student2 = new Student("小红");

        student1.showInfo();
        student2.showInfo();
        System.out.println("学生数量：" + Student.getCount());

        System.out.println("----------------");

        Student.setSchool("计算机学院");

        student1.showInfo();
        student2.showInfo();
    }
}
