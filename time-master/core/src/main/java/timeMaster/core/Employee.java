package timeMaster.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Employee {
    
    private String id;
    private String name;
    private ArrayList<Workday> workdays = new ArrayList<>();

    public Employee(String name) {
        this.id = generateId();
        this.name = name;
    }

    // Only for filewriters/readers
    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String generateId() {
        // TODO: create hashseed for ids
        return "" + new Random().nextInt();
    }

    private Boolean isWorkdayValid(Workday workday) {
        return !this.workdays.contains(workday);
    }

    public String getId() { return this.id; }

    public String getName() { return this.name; }

    public void addWorkday(Workday workday) {
        if (!this.isWorkdayValid(workday)) throw new IllegalArgumentException("Workday is already added.");
        this.workdays.add(workday); 
    }

    public ArrayList<Workday> getWorkdays() {
        return new ArrayList<>(this.workdays);
    }

    public Workday getDate(LocalDate date) {
        return this.workdays.stream().filter(e -> e.getDate().equals(date)).findAny().get();
    }

    @Override
    public String toString() {
        return this.id + "," + this.getName();
    }

    
}
