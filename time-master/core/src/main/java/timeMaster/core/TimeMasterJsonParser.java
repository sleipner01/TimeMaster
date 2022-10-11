package timeMaster.core;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TimeMasterJsonParser {
    
    ObjectMapper mapper;

    public TimeMasterJsonParser() {
        mapper = new ObjectMapper();
    }

    public void write(ArrayList<Employee> employees) {
        try {
            this.mapper.writeValue(new File("test.json"), employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Employee> read() {

        var employees = new ArrayList<Employee>();

        try {
            employees = this.mapper.readValue(new File("test.json"), 
                new TypeReference<ArrayList<Employee>> () {});

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        for (int i = 0; i < employees.size(); i++) {
            System.out.print(employees.get(i).toString());
            System.out.println("," + employees.get(i).getWorkdays());
        }

        return employees;
    }

        
    public static void main(String[] args) {
        var employees = new ArrayList<Employee>();
        for (int i = 0; i < 10; i++) {
            var employee = new Employee("test" + i);
            // employee.addWorkday(new Workday());
            employees.add(employee);
        }
        
        var parser = new TimeMasterJsonParser();
        
        parser.write(employees);

        parser.read();
    }

}
