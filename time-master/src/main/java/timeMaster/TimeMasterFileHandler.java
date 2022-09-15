package timeMaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

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

    public ArrayList<Employee> readEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filenameEmployees))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                String name = parts[1];
                employees.add(new Employee(name));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fil finnes ikke");
        }

        try (Scanner scanner = new Scanner(new File(filenameWorkdays))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");

                int employeeId = Integer.parseInt(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                LocalTime timeIn = LocalTime.parse(parts[2]);
                String timeOut = parts[3];

                Workday workday = new Workday(date, timeIn);
                if (!timeOut.equals("null")) {
                    workday.setTimeOut(LocalTime.parse(timeOut));
                }

                Employee employee = employees.get(employeeId);
                employee.addWorkday(workday);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fil finnes ikke");
        }
        return new ArrayList<>(employees);
    }

}
