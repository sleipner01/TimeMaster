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
}
