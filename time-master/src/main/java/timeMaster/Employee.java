package timeMaster;

import java.util.ArrayList;

public class Employee {
    
    private String name;
    private ArrayList<Workday> workdays = new ArrayList<>();

    public Employee(String name) {
        this.name = name;
    }

    private Boolean isWorkdayValid(Workday workday) {
        return !this.workdays.contains(workday);
    }

    public String getName() {
        return this.name;
    }

    public void addWorkday(Workday workday) {
        if (this.isWorkdayValid(workday)) {
           this.workdays.add(workday); 
        }
        else {
            throw new IllegalArgumentException("Workday is already added.");
        }
    }

    public ArrayList<Workday> getWorkdays() {
        return new ArrayList<>(this.workdays);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    
}
