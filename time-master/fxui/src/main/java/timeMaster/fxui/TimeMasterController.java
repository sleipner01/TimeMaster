package timeMaster.fxui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import timeMaster.core.Employee;
import timeMaster.core.TimeMaster;
import timeMaster.core.TimeMasterJsonParser;
// import timeMaster.core.Workday;

public class TimeMasterController {

    private TimeMaster timeMaster;

    @FXML private MenuButton chooseEmployeeButton;
    @FXML private Button registerTimeButton, autoRegisterTimeButton;
    @FXML private DatePicker chooseDateButton;
    @FXML private TextField inputHour, inputMinutes, newEmployeeName;
    @FXML private VBox autoCheckInOutBox, manualCheckInOutBox;
    @FXML private Circle statusIndicator;
    @FXML private Text statusText, clockInInfo;
    

    //DONE
    @FXML private void initialize() {
        this.timeMaster = new TimeMaster();

        this.chooseDateButton.setValue(timeMaster.getCurrentDate());
        timeMaster.readEmployees();
        this.updateEmployeeMenu();
    }

    //DONE
    // Using values from chooseDateButton, inputHour and inputMinutes, to create a Workday object for the user
    @FXML private void handleRegisterTime() {

        try {
            timeMaster.clockEmployeeInOut(chooseDateButton.getValue(),
            LocalTime.of(Integer.parseInt(this.inputHour.getText()),
                    Integer.parseInt(this.inputMinutes.getText())));

            this.clearTimeInputs();
            this.setTimeRegisterInputs();
        }
        catch (IllegalStateException e) {
            displayError(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            displayError(e.getMessage());
        }
    }

    //DONE
    @FXML private void autoClockInOut() {
        try {
            timeMaster.autoClockEmployeeInOut();
            setTimeRegisterInputs();
        }
        catch (IllegalStateException e) {
            displayError(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            displayError(e.getMessage());
        }
    }

    //DONE
    private void setTimeRegisterInputs() {
        if(timeMaster.getChosenEmployee() == null) {
            autoCheckInOutBox.setDisable(true);
            manualCheckInOutBox.setDisable(true);
        }
        else {
            autoCheckInOutBox.setDisable(false);
            manualCheckInOutBox.setDisable(false);
        }
        setEmployeeStatus();
    }

    //DONE
    private void setEmployeeStatus() {
        setStatusIndicator();
        setStatusText();
        setTimeRegisterButtons();
        setClockInInfoLabel();
    }

    //DONE
    private void setStatusIndicator() {
        if(timeMaster.getChosenEmployee().isAtWork()) statusIndicator.setFill(Color.GREEN);
        else statusIndicator.setFill(Color.GRAY);
    }

    //DONE
    private void setStatusText() {
        if(timeMaster.getChosenEmployee().isAtWork()) statusText.setText("Active");
        else statusText.setText("Off");
    }

    //DONE
    private void setTimeRegisterButtons() {
        if(timeMaster.getChosenEmployee().isAtWork()) registerTimeButton.setText("Check out");
        else registerTimeButton.setText("Check in");

        if(timeMaster.getChosenEmployee().isAtWork()) autoRegisterTimeButton.setText("Check out");
        else autoRegisterTimeButton.setText("Check in");
    }

    //DONE
    private void setClockInInfoLabel() {
        if(timeMaster.getChosenEmployee().isAtWork()) clockInInfo.setText("Clocked in at: " + timeMaster.getChosenEmployee().getLatestClockIn());
        else clockInInfo.setText(null);
    }
 
    private void clearTimeInputs() {
        this.clearInputHour();
        this.clearInputMinutes();
    }

    private void clearInputHour() { this.inputHour.clear(); }
    private void clearInputMinutes() { this.inputMinutes.clear(); }

    // Done
    // Lagre alle ansatte
    private void saveEmployees() {
        timeMaster.saveEmployees();
    }

    // Done
    // Les inn alle ansatte
    private void readEmployees() {
        timeMaster.readEmployees();
    }

    private void updateEmployeeMenu() {
        this.chooseEmployeeButton.getItems().clear();
        ArrayList<Employee> employees = timeMaster.getEmployees();
        for (int i = 0; i < employees.size(); i++) {
            MenuItem menuItem = new MenuItem(employees.get(i).getName());
            System.out.println(employees.get(i).getName());

            // ActionEvent a will not be used
            final int index = i;
            menuItem.setOnAction(a -> setChosenEmployee(index));

            // Legger til i ansattmenyen
            this.chooseEmployeeButton.getItems().add(menuItem);
        }
    }

    //DONE
    private void setChosenEmployee(int index) {
        timeMaster.setChosenEmployee(index);
        this.chooseEmployeeButton.setText(timeMaster.getChosenEmployee().getName());
        this.setTimeRegisterInputs();
    }

    private void createEmployee(String name) {
        try {
            timeMaster.createEmployee(name);
        }
        catch(IllegalArgumentException e) {
            displayError(e.getMessage());
        }
        catch(Exception e) {
            e.printStackTrace();
            displayError(e.getMessage());
        }
    }

    // TODO: check input, handle execption when empty, and validate name
    @FXML
    private void handleCreateEmployee() {
        String name = newEmployeeName.getText();
        createEmployee(name);
        newEmployeeName.clear();
        updateEmployeeMenu();
    }

    private void displayError(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("The program ecountered a problem!");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
