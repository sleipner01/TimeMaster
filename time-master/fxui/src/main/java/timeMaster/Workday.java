package timeMaster;

import java.time.LocalDate;
import java.time.LocalTime;

public class Workday {
    
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;

    public Workday(LocalDate date, LocalTime timeIn) {
        this.date = date;
        this.timeIn = timeIn;
    }

    public LocalDate getDate() { return date; }

    public LocalTime getTimeIn() { return timeIn; }

    public LocalTime getTimeOut() { return timeOut; }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public String toString() {
        return this.getDate().toString() + "," + this.getTimeIn() + "," + this.getTimeOut();
    }
}
