package timeMaster;

import java.util.ArrayList;

public class Employee {
    
    private String name;
    private ArrayList<Workday> workdays = new ArrayList<>();

    public Employee(String name) {
        this.name = name;
    }

    public void addWorkday(Workday workday) {
        this.workdays.add(workday);
    }
}
