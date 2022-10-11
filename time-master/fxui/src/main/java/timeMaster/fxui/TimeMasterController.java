package timeMaster.fxui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import timeMaster.core.Employee;
// import timeMaster.core.Workday;
import timeMaster.core.TimeMasterFileHandler;

public class TimeMasterController {

    private Employee chosenEmployee;
    // private Workday chosenWorkday;
    private Path saveDirPath;
    private TimeMasterFileHandler timeMasterFileHandler;
    private ArrayList<Employee> employees;

    @FXML private MenuButton chooseEmployeeButton;
    @FXML private Button registerTimeButton, autoRegisterTimeButton;
    @FXML private DatePicker chooseDateButton;
    @FXML private TextField inputHour, inputMinutes, newEmployeeName;
    @FXML private VBox autoCheckInOutBox, manualCheckInOutBox;
    @FXML private Circle statusIndicator;
    @FXML private Text statusText, clockInInfo;
    

    @FXML private void initialize() {
        this.saveDirPath = Paths.get(System.getProperty("user.dir"), "../core/timeMasterSaveFiles");
        System.out.println(saveDirPath);
        System.out.println(saveDirPath);
        this.timeMasterFileHandler = new TimeMasterFileHandler(saveDirPath);

        this.employees = new ArrayList<>();
        this.chooseDateButton.setValue(LocalDate.now());

        this.readEmployees();
        this.updateEmployeeMenu();

        // TODO: keep fields disabled until you choose an employee
    }

    // Using values from chooseDateButton, inputHour and inputMinutes, to create a Workday object for the user
    @FXML private void handleRegisterTime() {

        try {
            LocalDate date = chooseDateButton.getValue();
            LocalTime chosenTime = LocalTime.of(Integer.parseInt(this.inputHour.getText()),
                                   Integer.parseInt(this.inputMinutes.getText()));

            if(!this.chosenEmployee.isAtWork()) {
                this.chosenEmployee.checkIn(date, chosenTime);
                registerTimeButton.setText("Check out");
            }
            else {
                this.chosenEmployee.checkOut(chosenTime);
                registerTimeButton.setText("Check in");
            }
            
            this.saveEmployees();

            this.clearTimeInputs();
            this.setTimeRegisterInputs();

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML private void autoClockInOut() {
        try {

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            if(!this.chosenEmployee.isAtWork()) {
                this.chosenEmployee.checkIn(date, time);
                autoRegisterTimeButton.setText("Check out");
            }
            else {
                this.chosenEmployee.checkOut(time);
                autoRegisterTimeButton.setText("Check in");
            }
            
            this.saveEmployees();
            setTimeRegisterInputs();
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void setTimeRegisterInputs() {
        if(chosenEmployee == null) {
            autoCheckInOutBox.setDisable(true);
            manualCheckInOutBox.setDisable(true);
        }
        else {
            autoCheckInOutBox.setDisable(false);
            manualCheckInOutBox.setDisable(false);
        }
        setEmployeeStatus();

    }

    private void setEmployeeStatus() {
        setStatusIndicator();
        setStatusText();
        setTimeRegisterButtons();
        setClockInInfoLabel();
    }

    // private void setEmployeeOff() {

    // }

    private void setStatusIndicator() {
        if(chosenEmployee.isAtWork()) statusIndicator.setFill(Color.GREEN);
        else statusIndicator.setFill(Color.GRAY);
    }

    private void setStatusText() {
        if(chosenEmployee.isAtWork()) statusText.setText("Active");
        else statusText.setText("Off");
    }

    private void setTimeRegisterButtons() {
        if(chosenEmployee.isAtWork()) registerTimeButton.setText("Check out");
        else registerTimeButton.setText("Check in");

        if(chosenEmployee.isAtWork()) autoRegisterTimeButton.setText("Check out");
        else autoRegisterTimeButton.setText("Check in");
    }

    private void setClockInInfoLabel() {
        if(chosenEmployee.isAtWork()) clockInInfo.setText("Clocked in at: " + chosenEmployee.getLatestClockIn());
        else clockInInfo.setText(null);
    }
 
    private void clearTimeInputs() {
        this.clearInputHour();
        this.clearInputMinutes();
    }
    private void clearInputHour() { this.inputHour.clear(); }
    private void clearInputMinutes() { this.inputMinutes.clear(); }

    // Lagre alle ansatte
    private void saveEmployees() { this.timeMasterFileHandler.writeEmployees(this.employees); }
    // Les inn alle ansatte
    private void readEmployees() { this.employees = this.timeMasterFileHandler.readEmployees(); }

    private void updateEmployeeMenu() {
        this.chooseEmployeeButton.getItems().clear();
        for (int i = 0; i < this.employees.size(); i++) {
            int employeeIndex = i;
            MenuItem menuItem = new MenuItem(this.getEmployee(employeeIndex).getName());

            // Ta ActionEventet "a" som input til lambda-uttrykket selv om vi ikke bruker det
            menuItem.setOnAction( a -> setChosenEmployee(employeeIndex) );
            
            // Legger til i ansattmenyen
            this.chooseEmployeeButton.getItems().add(menuItem); 
        }
    }

    private Employee getEmployee(int index) { return employees.get(index); }

    private void setChosenEmployee(int index) {
        this.chosenEmployee = this.getEmployee(index);
        this.chooseEmployeeButton.setText(this.chosenEmployee.getName());
        this.setTimeRegisterInputs();
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
