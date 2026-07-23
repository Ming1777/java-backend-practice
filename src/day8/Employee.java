package day8;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
public class Employee  {//员工
    private String name;
    private double salary;

    public void work(){
        System.out.println(name + "正在工作");

    }
}
