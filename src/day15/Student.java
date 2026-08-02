package day15;

public class Student {
    private String name;

    private static String school = "软件学院";
    private static int count = 0;

    public Student(String name) {
        this.name = name;
        count++;
    }

    public void showInfo() {
        System.out.println("姓名：" + name);
        System.out.println("学院：" + school);
    }

    public static String getSchool() {
        return school;
    }

    public static void setSchool(String school) {
        Student.school = school;
    }

    public static int getCount() {
        return count;
    }
}
