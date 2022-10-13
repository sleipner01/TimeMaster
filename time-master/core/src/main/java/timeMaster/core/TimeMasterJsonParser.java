package timeMaster.core;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import timeMaster.mixin.MixIn;

public class TimeMasterJsonParser {

    final ObjectMapper mapper;
    final String filePath;
    final String fileName = "employees.json";

    public TimeMasterJsonParser(Path dir) {
        this.mapper = new ObjectMapper();
        this.mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
        this.mapper.addMixIn(Employee.class, MixIn.class);
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.registerModule(new JavaTimeModule());
        this.filePath = Paths.get(dir.toString(), fileName).toString();
    }

    public void write(ArrayList<Employee> employees) {
        try {
            this.mapper.writeValue(new File(filePath), employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Employee> read() {
        var employees = new ArrayList<Employee>();
        if(new File(filePath).length() == 0) {
            System.out.println("Savefile is empty");
            return new ArrayList<>();
        }
        try {
            employees = this.mapper.readValue(
                    new File(filePath),
                    new TypeReference<ArrayList<Employee>>() {
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }

}
