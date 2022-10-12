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
import timeMaster.core.TimeMasterJsonParser;
import timeMaster.core.Workday;

public class TimeMasterController {

    private Employee chosenEmployee;
    private Workday chosenWorkday;
    private Path saveDirPath;
    private TimeMasterJsonParser jsonParser;
    private ArrayList<Employee> employees;

    @FXML private MenuButton chooseEmployeeButton;
    @FXML private DatePicker chooseDateButton;
    @FXML private TextField inputHour;
    @FXML private TextField inputMinutes;
    @FXML private TextField newEmployeeName;

    @FXML
    private void initialize() {
        this.saveDirPath = Paths.get(System.getProperty("user.dir"), "../core/timeMasterSaveFiles");
        this.jsonParser = new TimeMasterJsonParser(saveDirPath);
        this.chooseDateButton.setValue(LocalDate.now());

        this.readEmployees();
        this.updateEmployeeMenu();
    }

    // Using values from chooseDateButton, inputHour and inputMinutes, to create a Workday object for the user
    @FXML
    private void handleRegisterTime() {
        try {
            LocalDate date = chooseDateButton.getValue();
            LocalTime chosenTime = LocalTime.of(Integer.parseInt(this.inputHour.getText()),
                    Integer.parseInt(this.inputMinutes.getText()));

            if (this.chosenEmployee.getWorkdays().stream().anyMatch(e -> e.getDate().equals(date))) {
                this.chosenWorkday = this.chosenEmployee.getDate(date);
                this.chosenWorkday.setTimeOut(chosenTime);
            } else {
                this.chosenEmployee.addWorkday(new Workday(date, chosenTime));
            }
            this.saveEmployees();

            this.inputHour.clear();
            this.inputMinutes.clear();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // Lagre alle ansatte
    private void saveEmployees() {
        this.jsonParser.write(this.employees);
    }

    // Les inn alle ansatte
    private void readEmployees() {
        this.employees = this.jsonParser.read();
    }

    private void updateEmployeeMenu() {
        this.chooseEmployeeButton.getItems().clear();
        for (int i = 0; i < this.employees.size(); i++) {
            MenuItem menuItem = new MenuItem(this.employees.get(i).getName());
            System.out.println(this.employees.get(i).getName());

            // Ta ActionEventet "a" som input til lambda-uttrykket selv om vi ikke bruker det
            final int index = i;
            menuItem.setOnAction(a -> setChosenEmployee(index));

            // Legger til i ansattmenyen
            this.chooseEmployeeButton.getItems().add(menuItem);
        }
    }

    private void setChosenEmployee(int index) {
        this.chosenEmployee = this.employees.get(index);
        this.chooseEmployeeButton.setText(this.chosenEmployee.getName());
    }

    // creates new employee based on input
    private void createEmployee(String name) {
        if (name.equals("")) {
            throw new IllegalArgumentException("Input required, please enter name");
        }
        this.employees.add(new Employee(name));
        this.saveEmployees();
    }

    // TODO: check input, handle execption when empty, and validate name
    @FXML
    private void handleCreateEmployee() {
        String name = newEmployeeName.getText();
        createEmployee(name);
        newEmployeeName.clear();
        updateEmployeeMenu();
    }
}
