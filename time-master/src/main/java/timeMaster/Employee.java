package timeMaster;

import java.time.LocalDate;
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

    public Workday getDate(LocalDate date) {
        return this.workdays.stream().filter(e -> e.getDate().equals(date)).findAny().get();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    
}
