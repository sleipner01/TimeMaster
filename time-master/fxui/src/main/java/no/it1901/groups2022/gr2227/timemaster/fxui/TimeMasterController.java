package no.it1901.groups2022.gr2227.timemaster.fxui;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import no.it1901.groups2022.gr2227.timemaster.core.Employee;
import no.it1901.groups2022.gr2227.timemaster.core.TimeMaster;

public class TimeMasterController {
  
  private TimeMaster timeMaster;
  
  @FXML private MenuButton chooseEmployeeButton;
  @FXML private Button registerTimeButton;
  @FXML private Button autoRegisterTimeButton;
  @FXML private DatePicker chooseDateButton;
  @FXML private TextField inputHour;
  @FXML private TextField inputMinutes; 
  @FXML private TextField newEmployeeName;
  @FXML private VBox autoCheckInOutBox;
  @FXML private VBox manualCheckInOutBox;
  @FXML private Circle statusIndicator;
  @FXML private Text statusText;
  @FXML private Text clockInInfo;
  
  @FXML private void initialize() throws ConnectException {
    try {
      this.chooseDateButton.setValue(LocalDate.now());
      this.timeMaster = new TimeMaster();
      this.timeMaster.readEmployees();
      this.updateEmployeeMenu();
    } catch (IOException e) {
      displayError(e.getMessage() + ", make sure the REST API is running.");
    }
  }

  /**
   * For testing purposes. Turning off API calls.
   */
  public void setApplicationInTestState() {
    timeMaster.setApplicationInTestState();
  }

  /**
   * For testing purposes.
   * Resetting to Production state if needed after testing.
   */
  public void setApplicationInProductionState() {
    timeMaster.setApplicationInProductionState();
  }
  
  
  @FXML private void handleRegisterTime() {
    try {
      timeMaster.clockEmployeeInOut(chooseDateButton.getValue(),
          LocalTime.of(Integer.parseInt(this.inputHour.getText()),
          Integer.parseInt(this.inputMinutes.getText())));
      
      this.clearTimeInputs();
      this.setTimeRegisterInputs();
    } catch (IllegalStateException e) {
      displayError(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      displayError(e.getMessage());
    }
  }
  
  @FXML private void autoClockInOut() {
    try {
      timeMaster.autoClockEmployeeInOut();
      setTimeRegisterInputs();
    } catch (IllegalStateException e) {
      displayError(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      displayError(e.getMessage());
    }
  }
  
  private void setTimeRegisterInputs() {
    if (timeMaster.getChosenEmployee() == null) {
      autoCheckInOutBox.setDisable(true);
      manualCheckInOutBox.setDisable(true);
    } else {
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
  
  private void setStatusIndicator() {
    if (timeMaster.getChosenEmployee().isAtWork()) { 
      statusIndicator.setFill(Color.GREEN);
    } else {
      statusIndicator.setFill(Color.GRAY);
    }
  }
  
  private void setStatusText() {
    if (timeMaster.getChosenEmployee().isAtWork()) {
      statusText.setText("Active");
    } else {
      statusText.setText("Off");
    }
  }
  
  private void setTimeRegisterButtons() {
    if (timeMaster.getChosenEmployee().isAtWork()) {
      registerTimeButton.setText("Check out");
      autoRegisterTimeButton.setText("Check out");
    } else {
      registerTimeButton.setText("Check in");
      autoRegisterTimeButton.setText("Check in");
    }
  }
  
  private void setClockInInfoLabel() {
    if (timeMaster.getChosenEmployee().isAtWork()) {
      clockInInfo.setText("Clocked in at: " + timeMaster.getChosenEmployee().getLatestClockIn());
    } else {
      clockInInfo.setText(null);
    }
  }
  
  private void clearTimeInputs() {
    this.inputHour.clear();
    this.inputMinutes.clear();
  }
  
  private void updateEmployeeMenu() {
    this.chooseEmployeeButton.getItems().clear();
    ArrayList<Employee> employees = timeMaster.getEmployees();
    for (int i = 0; i < employees.size(); i++) {
      MenuItem menuItem = new MenuItem(employees.get(i).getName());
      //System.out.println(employees.get(i).getName());
      
      // ActionEvent a will not be used
      final int index = i;
      menuItem.setOnAction(a -> setChosenEmployee(index));
      
      // Adding to employee-menu
      this.chooseEmployeeButton.getItems().add(menuItem);
    }
  }
  
  private void setChosenEmployee(int index) {
    try {
      timeMaster.setChosenEmployee(index);
      this.chooseEmployeeButton.setText(timeMaster.getChosenEmployee().getName());
      this.setTimeRegisterInputs();
    } catch (Exception e) {
      e.printStackTrace();
      displayError(e.getMessage());
    }
  }
  
  
  @FXML private void handleCreateEmployee() {
    try {
      timeMaster.createEmployee(newEmployeeName.getText());
    } catch (IllegalArgumentException e) {
      displayError(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      displayError(e.getMessage());
    }
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
