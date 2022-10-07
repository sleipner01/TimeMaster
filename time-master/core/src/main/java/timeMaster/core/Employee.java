package timeMaster.core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

public class Employee {
    
    private String id;
    private String name;
    private ArrayList<Workday> workdays = new ArrayList<>();
    private boolean atWork;

    public Employee(String name) {
        this.id = generateId();
        this.name = name;
    }

    // Only for filewriters/readers
    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String generateId() { return UUID.randomUUID().toString(); }

    private Boolean isWorkdayValid(Workday workday) {
        return !this.workdays.contains(workday);
    }

    public void checkIn(LocalDate date, LocalTime time) {
        if(isAtWork()) throw new IllegalStateException(this.toString() + " is already at work!");
        this.workdays.add(0, new Workday(date, time));
        this.atWork = true;
    }

    public void checkOut(LocalTime time) {
        if(!isAtWork()) throw new IllegalStateException(this.toString() + " is not at work!");
        this.workdays.get(0).setTimeOut(time);
        this.atWork = false;
    }

    public String getId() { return this.id; }

    public String getName() { return this.name; }

    public boolean isAtWork() { return this.atWork; }

    public void addWorkday(Workday workday) {
        if (!this.isWorkdayValid(workday)) throw new IllegalArgumentException("Workday is already added.");
        this.workdays.add(0, workday);
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
