package timeMaster.fxui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import timeMaster.core.Employee;
import timeMaster.core.Workday;
import timeMaster.core.TimeMasterFileHandler;
import timeMaster.core.TimeMasterJsonParser;

public class TimeMasterController {

    private Employee chosenEmployee;
    private Workday chosenWorkday;
    private Path saveDirPath;
    private TimeMasterFileHandler timeMasterFileHandler;
    private TimeMasterJsonParser jsonParser;
    private ArrayList<Employee> employees;

    @FXML private MenuButton chooseEmployeeButton;
    @FXML private DatePicker chooseDateButton;
    @FXML private TextField inputHour, inputMinutes, newEmployeeName;
    

    @FXML private void initialize() {
        this.saveDirPath = Paths.get(System.getProperty("user.dir"), "time-master/core/timeMasterSaveFiles");
        System.out.println(saveDirPath);
        System.out.println(saveDirPath);
        this.timeMasterFileHandler = new TimeMasterFileHandler(saveDirPath);
        this.jsonParser = new TimeMasterJsonParser(saveDirPath);

        this.employees = new ArrayList<>();
        this.chooseDateButton.setValue(LocalDate.now());

        this.readEmployees();
        // this.saveEmployees();
        this.updateEmployeeMenu();
    }

    // Using values from chooseDateButton, inputHour and inputMinutes, to create a Workday object for the user
    @FXML private void handleRegisterTime() {
        try {
            LocalDate date = chooseDateButton.getValue();
            LocalTime chosenTime = LocalTime.of(Integer.parseInt(this.inputHour.getText()),
                                   Integer.parseInt(this.inputMinutes.getText()));

            if (this.chosenEmployee.getWorkdays().stream().anyMatch(e -> e.getDate().equals(date))) {
                this.chosenWorkday = this.chosenEmployee.getDate(date);
                this.chosenWorkday.setTimeOut(chosenTime);
            } 
            else this.chosenEmployee.addWorkday(new Workday(date, chosenTime));
            
            this.saveEmployees();

            this.clearInputHour();
            this.clearInputMinutes();

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void clearInputs() {
        this.clearInputHour();
        this.clearInputMinutes();
    }
    private void clearInputHour() { this.inputHour.clear(); }
    private void clearInputMinutes() { this.inputMinutes.clear(); }

    // Lagre alle ansatte
    private void saveEmployees() { 
        // this.timeMasterFileHandler.writeEmployees(this.employees);
        this.jsonParser.write(this.employees);
    }
    // Les inn alle ansatte
    private void readEmployees() { 
        // this.employees = this.timeMasterFileHandler.readEmployees();
        this.jsonParser.read();
    }

    private void updateEmployeeMenu() {
        this.chooseEmployeeButton.getItems().clear();
        for (int i = 0; i < this.employees.size(); i++) {
            var employeeIndex = i;
            MenuItem menuItem = new MenuItem(getEmployee(employeeIndex).getName());
            System.out.println(getEmployee(employeeIndex).getName());

            // Ta ActionEventet "a" som input til lambda-uttrykket selv om vi ikke bruker det
            menuItem.setOnAction( a -> setChosenEmployee(employeeIndex) );
            
            // Legger til i ansattmenyen
            this.chooseEmployeeButton.getItems().add(menuItem); 
        }
    }

    private Employee getEmployee(int index) { return employees.get(index); }

    private void setChosenEmployee(int index) {
        this.chosenEmployee = getEmployee(index);
        this.chooseEmployeeButton.setText(this.chosenEmployee.getName());
    }

    //creates new employee based on input 
    private void createEmployee(String name){
        if(name.equals("") ){
            throw new IllegalArgumentException("Input required, please enter name");
        }
        this.employees.add(new Employee(name));
        this.saveEmployees();
    }



    //TODO: check input, handle execption when empty, and validate name
     
    @FXML private void handleCreateEmployee(){
        String name = newEmployeeName.getText();
        createEmployee(name);
        updateEmployeeMenu();
    }
}
