package timeMaster.core;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class TimeMasterJsonParser {
    
    ObjectMapper mapper = new ObjectMapper();
    CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Employee.class);

    public void write(ArrayList<Employee> employees) {
        try {
            mapper.writeValue(new File("test.json"), employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Employee> read() {

        var employees = new ArrayList<Employee>();

        try {
            var test = mapper.readValue(new File("test.json"), ArrayList.class);
            // var test = mapper.readValue(new File("test.json"), new TypeReference<ArrayList<Employee>> () {});
            // var test = mapper.readValue(new File("test.json"), listType);

            System.out.println("\n" + test + "\n");

            // ArrayList<Employee> test1 = mapper.convertValue(test, listType);
            
            for (int i = 0; i < test.size(); i++) {
                System.out.println("\n" + test.get(i).getClass());
                // var employee = (Employee) test.get(i);
                // System.out.println(employee.getClass());
                // employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            System.out.println(employee.toString());
        }

        return employees;
    }

        
    public static void main(String[] args) {
        var employees = new ArrayList<Employee>();
        employees.add(new Employee("test1"));
        employees.add(new Employee("test2"));
        employees.add(new Employee("test3"));
        
        var parser = new TimeMasterJsonParser();
        
        parser.write(employees);

        parser.read();
    }

}
