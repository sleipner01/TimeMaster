package timeMaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class TimeMasterFileHandler {

    private final String employeesFileName = "employees.csv";
    private final String workdaysFileName = "workdays.csv";
    private final String seperator = ",";

    private String saveDirectory;
    private String employeesFilePath;
    private String workdaysFilePath;

    public TimeMasterFileHandler(Path saveDirectory) {
        this.saveDirectory = saveDirectory.toString();
        this.employeesFilePath = Paths.get(this.saveDirectory, employeesFileName).toString();
        this.workdaysFilePath = Paths.get(this.saveDirectory, workdaysFileName).toString();
    }

    public void writeEmployees(ArrayList<Employee> employees) {
        try {
            PrintWriter writerEmployees = new PrintWriter(employeesFilePath);
            PrintWriter writerWorkdays = new PrintWriter(workdaysFilePath);

            writerEmployees.println("id" + seperator + "name");
            writerWorkdays.println("employeeId" + seperator + "date" + seperator + "timeIn" + seperator + "timeOut");

            for (int i = 0; i < employees.size(); i++) {
                Employee employee = employees.get(i);
                writerEmployees.println(employee.toString());

                employee.getWorkdays().forEach(workday -> writerWorkdays.println(employee.getId() + seperator + workday.toString()));
            }

            writerEmployees.close();
            writerWorkdays.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("One or more files could not be created");
        }
    }

    public ArrayList<Employee> readEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(employeesFilePath))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(seperator.toString());
                String id = parts[0];
                String name = parts[1];
                employees.add(new Employee(id, name));
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("The file wasn't found on path: " + employeesFilePath);
        }

        try (Scanner scanner = new Scanner(new File(workdaysFilePath))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(seperator);

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
        } 
        catch (FileNotFoundException e) {
            System.out.println("The file wasn't found on path: " + workdaysFilePath);
        }

        return new ArrayList<>(employees);
    }

}
