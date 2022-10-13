package timeMaster.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

public class TimeMaster {

    private Employee chosenEmployee;
    // private Workday chosenWorkday;
    private Path saveDirPath;
    private TimeMasterJsonParser jsonParser;
    private ArrayList<Employee> employees;

    public TimeMaster() {
        this.saveDirPath = Paths.get(System.getProperty("user.dir"), "../core/timeMasterSaveFiles");
        this.jsonParser = new TimeMasterJsonParser(saveDirPath, "employees.json");
    }

    public LocalDate getCurrentDate() { return LocalDate.now(); }

    
    
}
