package timeMaster;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TimeMasterFileHandler {
    
    private String saveDirectory;
    private String filenameEmployees;
    private String filenameWorkdays;

    public TimeMasterFileHandler(String saveDirectory) {
        this.saveDirectory = saveDirectory;
        this.filenameEmployees = saveDirectory + "employees.csv";
        this.filenameWorkdays = saveDirectory + "workdays.csv";
    }

    public void writeEmployees(ArrayList<Employee> employees) {
        try (PrintWriter writerEmployees = new PrintWriter(filenameEmployees)) {
            try (PrintWriter writerWorkdays = new PrintWriter(filenameWorkdays)) {
                writerEmployees.println("id;name;birthday;salary");
                writerWorkdays.println("employeeId;date;timeIn;timeOut;comment");

                for (int i = 0; i < employees.size(); i++) {
                    Employee employee = employees.get(i);
                    writerEmployees.println(i + ";" + employee.toString());
                    
                    for (Workday workday : employee.getWorkdays()) {
                        writerWorkdays.println(i + ";" + workday.toString());
                    }
                }
            } catch (IOException e) {
                System.out.println("Filen " + filenameWorkdays + " kunne ikke oprettes");
            }
        } catch (IOException e) {
            System.out.println("Filen " + filenameEmployees + " kunne ikke oprettes");
        } 
    }

}
