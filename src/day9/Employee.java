package day9;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Employee {
    private String name;
    private double salary;

    public abstract void work();

    public void showInfo() {
        System.out.println("姓名：" + name);
        System.out.println("工资：" + salary);
    }
}

