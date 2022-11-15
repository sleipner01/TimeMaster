package no.it1901.groups2022.gr2227.timemaster.fxui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import no.it1901.groups2022.gr2227.timemaster.core.Employee;
import no.it1901.groups2022.gr2227.timemaster.core.TimeMaster;
import no.it1901.groups2022.gr2227.timemaster.core.Workday;

/**
 * Controller for the TimeMaster application.
 * Connects the FXUI module with the Core module.
 * Initializing this class will create a TimeMaster-object 
 * and start the core-module.
 *
 * <p>The controller aims to follow the MVC principle (Model-View-Controller).
 * No logic around the application is stored in this class. 
 * Any overhead logic will be stored in {@link TimeMaster}.
 *
 * <p>The methods in this controller will include:
 * <ul>
 * <li>Initialization
 * <li>Conditional information about other modules or similar  
 * <li>Input validation
 * <li>On-action handling
 * <li>UI updating 
 * </ul>
 *
 * @author Amalie Erdal Mansåker
 * @author Magnus Byrkjeland
 * @author Håvard Solberg Nybøe
 * @author Karen Gjersem Bakke
 * @version %I%, %G%
 * @since 1.0
 */
public class TimeMasterController {

  private TimeMaster timeMaster;
  private ObservableList<Employee> observableEmployeeList;
  private ObservableList<Workday> observableWorkdayList;

  @FXML
  private Button registerTimeButton;
  @FXML
  private Button autoRegisterTimeButton;
  @FXML
  private Button deleteEmployeeButton;
  @FXML
  private DatePicker chooseDateButton;
  @FXML
  private TextField inputHour;
  @FXML
  private TextField inputMinutes;
  @FXML
  private TextField newEmployeeName;
  @FXML
  private VBox autoCheckInOutBox;
  @FXML
  private VBox manualCheckInOutBox;
  @FXML
  private Circle statusIndicator;
  @FXML
  private Circle statusIndicatorApi;
  @FXML
  private Text statusText;
  @FXML
  private Text statusTextApi;
  @FXML
  private Text addStatus;
  @FXML
  private Text deleteStatus;
  @FXML
  private Text clockInInfo;  
  @FXML
  private Text stampInEmployeeName;
  @FXML
  private Text historyEmployeeName;
  @FXML 
  private MenuButton deleteEmployeeMenu;
  @FXML
  private ListView<Workday> workdayHistoryList;
  @FXML
  private ListView<Employee> chooseEmployeeListView;

  @FXML
  private void initialize() {
    this.chooseDateButton.setValue(LocalDate.now());
    this.timeMaster = new TimeMaster();
    chooseEmployeeListenerSetup();
    workDayHistoryListenerSetup();
    limitTextFieldToTwoNumbers(inputHour);
    limitTextFieldToTwoNumbers(inputMinutes);
    setApiStatus();
    updateDisplay();
  }

  /**
   * For testing purposes. Turning off API calls.
   */
  public void setApplicationInTestState() {
    timeMaster.setApplicationInTestState();
    setApiStatus();
  }

  /**
   * For testing purposes.
   * Resetting to Production state if needed after testing.
   */
  public void setApplicationInProductionState() {
    timeMaster.setApplicationInProductionState();
    setApiStatus();
  }

  private void workDayHistoryListenerSetup() {
    observableWorkdayList = FXCollections.observableArrayList();
    workdayHistoryList.setItems(observableWorkdayList);

    workdayHistoryList.setCellFactory(lv -> {
      ListCell<Workday> cell = new ListCell<Workday>() {
        @Override
        protected void updateItem(Workday item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText(null);
          } else {
            setText(item.toString());
          }
        }
      };
      cell.setOnMouseClicked(e -> {
        if (!cell.isEmpty()) {
          System.out.println("You clicked on " + cell.getItem());
          openWorkdayEditInterface(cell.getItem());
          e.consume();
        }
      });
      return cell;
    });
  }

  private void chooseEmployeeListenerSetup() {
    observableEmployeeList = FXCollections.observableArrayList();
    chooseEmployeeListView.setItems(observableEmployeeList);

    chooseEmployeeListView.setCellFactory(lv -> {
      ListCell<Employee> cell = new ListCell<Employee>() {
        @Override
        protected void updateItem(Employee item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText(null);
          } else {
            setText(item.getName());
          }
        }
      };
      cell.setOnMouseClicked(e -> {
        if (!cell.isEmpty()) {
          System.out.println("You clicked on " + cell.getItem());
          setChosenEmployee(cell.getItem());
          e.consume();
        }
      });
      return cell;
    });
  }

  @FXML
  private void handleRegisterTime() {

    String inputMinuteString = this.inputMinutes.getText();
    String inputHourString = this.inputHour.getText();
    // Creating a main boolean variable to be able to show all validation-errors.
    boolean validationFailure = false;
    if (!isValidMinuteInput(inputMinuteString)) {
      warningDialog(inputMinuteString + " is not a valid input for minute-in.");
      validationFailure = true;
    }

    if (!isValidHourInput(inputHourString)) {
      warningDialog(inputHourString + " is not a valid input for hour-in.");
      validationFailure = true;
    }
    if (validationFailure) {
      return;
    }

    LocalDate date = chooseDateButton.getValue();
    LocalTime time = LocalTime.of(Integer.parseInt(inputHourString),
        Integer.parseInt(inputMinuteString));
    LocalDateTime dateTime = LocalDateTime.of(date, time);

    try {
      timeMaster.clockEmployeeInOut(dateTime);
      this.clearTimeInputs();
      updateDisplay();
    } catch (IllegalStateException e) {
      displayError(e.getMessage());
    } catch (IllegalArgumentException e) {
      displayError(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      displayError(e.getMessage());
      setApiStatus();
    }
  }

  @FXML
  private void autoClockInOut() {
    try {
      timeMaster.autoClockEmployeeInOut();
      updateDisplay();
    } catch (IllegalStateException e) {
      displayError(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      displayError(e.getMessage());
      setApiStatus();
    }
  }

  private void updateDisplay() {
    setTimeRegisterInputs();
    setDeleteButtonStatus();
    setEmployeeStatus();
    updateEmployeeMenu();
    addStatus.setText(null);
    deleteStatus.setText(null);
  }

  private void setTimeRegisterInputs() {
    if (!timeMaster.employeeIsSet()) {
      autoCheckInOutBox.setDisable(true);
      manualCheckInOutBox.setDisable(true);
    } else {
      autoCheckInOutBox.setDisable(false);
      manualCheckInOutBox.setDisable(false);
    }
  }

  private void setEmployeeStatus() {
    setStatusIndicator();
    setStatusText();
    setTimeRegisterButtons();
    setClockInInfoLabel();
    showWorkdayHistory();
    setStampInEmployeeName();
    setHistoryEmployeeName();
  }

  private void setHistoryEmployeeName() {
    if (timeMaster.employeeIsSet()) {
      this.historyEmployeeName.setText(timeMaster.getChosenEmployee().getName());
    } else {
      this.historyEmployeeName.setText("Employee");
    }
  }

  private void setStampInEmployeeName() {
    if (timeMaster.employeeIsSet()) {
      this.stampInEmployeeName.setText(timeMaster.getChosenEmployee().getName());
    } else {
      this.stampInEmployeeName.setText("Employee");
    }
  }

  private void setDeleteButtonStatus() {
    if (!timeMaster.employeeIsSet()) {
      deleteEmployeeButton.setDisable(true);
      deleteEmployeeButton.setText("Not available");
    } else {
      deleteEmployeeButton.setDisable(false);
      deleteEmployeeButton.setText("Delete " + timeMaster.getChosenEmployee().getName());
    }
  }

  private void setStatusIndicator() {
    if (timeMaster.employeeIsSet() && timeMaster.getChosenEmployee().isAtWork()) {
      statusIndicator.setFill(Color.GREEN);
    } else {
      statusIndicator.setFill(Color.GRAY);
    }
  }

  private void setStatusText() {
    if (timeMaster.employeeIsSet() && timeMaster.getChosenEmployee().isAtWork()) {
      statusText.setText("Active");
    } else {
      statusText.setText("Off");
    }
  }

  private void setTimeRegisterButtons() {
    if (timeMaster.employeeIsSet() && timeMaster.getChosenEmployee().isAtWork()) {
      registerTimeButton.setText("Check out");
      autoRegisterTimeButton.setText("Check out");
    } else {
      registerTimeButton.setText("Check in");
      autoRegisterTimeButton.setText("Check in");
    }
  }

  private void setClockInInfoLabel() {
    if (timeMaster.employeeIsSet() && timeMaster.getChosenEmployee().isAtWork()) {
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
    observableEmployeeList.setAll(timeMaster.getEmployees());
  }

  /**
   * Check ApiStatus from TimeMaster.
   *
   * @return  <code>true</code> if API is online,
   *          else <code>false</code>
   */
  public boolean getApiStatus() {
    return timeMaster.getApiStatus();
  }

  /**
   * Check if TimeMaster is connected and using an API.
   *
   * @return  <code>true</code> if API is being used,
   *          else <code>false</code>
   */
  public boolean getIsUsingApi() {
    return timeMaster.isUsingApi();
  }

  private void setApiStatus() {
    if (this.getApiStatus() && getIsUsingApi()) {
      // Online
      statusIndicatorApi.setFill(Color.GREEN);
      statusTextApi.setText("Online");
    } else if (this.getApiStatus() && (!this.getIsUsingApi())) {
      // Available
      statusIndicatorApi.setFill(Color.BLUE);
      statusTextApi.setText("Available");
    } else if (((!this.getApiStatus()) && this.getIsUsingApi())) {
      // Using API but API is unavailable
      statusIndicatorApi.setFill(Color.RED);
      statusTextApi.setText("Error");
    } else {
      // Offline
      statusIndicatorApi.setFill(Color.GRAY);
      statusTextApi.setText("Offline");
    }
  }

  private void setDeleteStatus(boolean success) {
    if (success) {
      deleteStatus.setText("Success! Employee was deleted");
      deleteStatus.setFill(Color.GREEN);
    } else {
      deleteStatus.setText("Something went wrong...");
      deleteStatus.setFill(Color.RED);
    }
  }

  private void setChosenEmployee(Employee employee) {
    try {
      timeMaster.setChosenEmployee(employee);
      updateDisplay();
    } catch (Exception e) {
      e.printStackTrace();
      displayError(e.getMessage());
      setApiStatus();
    }
  }

  @FXML
  private void handleCreateEmployee() {
    try {
      timeMaster.createEmployee(newEmployeeName.getText());
      addStatus.setText("Created " + newEmployeeName.getText());
      addStatus.setFill(Color.GREEN);
      newEmployeeName.clear();
      updateEmployeeMenu();
    } catch (IllegalArgumentException e) {
      displayError(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      displayError(e.getMessage());
      setApiStatus();
    }

  }

  private void displayError(String errorMessage) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("The program encountered a problem!");
    alert.setContentText(errorMessage);
    alert.showAndWait();
  }

  private void showWorkdayHistory() {
    if (!timeMaster.employeeIsSet()) {
      this.emptyWorkdayHistory();
      return;
    }

    try {
      observableWorkdayList.setAll(timeMaster.getEmployeeWorkdayHistory());
    } catch (IllegalStateException e) {
      e.printStackTrace();
      displayError(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
      displayError(e.getMessage());
      emptyWorkdayHistory();
    }
  }

  private void emptyWorkdayHistory() {
    observableWorkdayList.clear();
    ;
  }

  private boolean confirmationDialog(String body) {

    Alert dialog = new Alert(AlertType.CONFIRMATION);
    dialog.setContentText(body);

    Optional<ButtonType> result = dialog.showAndWait();
    System.out.println(result.get());
    if (result.isPresent()) {
      switch (result.get().getButtonData()) {
        case OK_DONE:
          System.out.println("Confirmed");
          return true;
        case CANCEL_CLOSE:
          System.out.println("Cancelled");
          return false;
        default:
          displayError("The window wasn't closed properly...");
          return false;
      }
    } else {
      return false;
    }
  }

  private void warningDialog(String body) {
    Alert dialog = new Alert(AlertType.WARNING);
    dialog.setContentText(body);
    dialog.showAndWait();
  }

  private void limitTextFieldToTwoNumbers(TextField field) {
    int maxLength = 2;
    field.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          field.setText(newValue.replaceAll("[^\\d]", ""));
        }
        if (field.getText().length() > maxLength) {
          String s = field.getText().substring(0, maxLength);
          field.setText(s);
        }
      }
    });
  }

  private void openWorkdayEditInterface(Workday workday) {

    final ButtonType okButtonType = new ButtonType("Ok", ButtonData.APPLY);
    final ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    final ButtonType deleteButtonType = new ButtonType("Delete", ButtonData.OTHER);

    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Edit Workday");
    dialog.setHeaderText("Change workday values...");
    dialog.getDialogPane().getButtonTypes()
        .addAll(okButtonType, cancelButtonType, deleteButtonType);

    // Time in inputs
    final Label labelIn = new Label("Time in");
    DatePicker dateIn = new DatePicker();
    dateIn.setId("editDialogDateIn");
    TextField timeInHour = new TextField();
    timeInHour.setId("editDialogTimeInHour");
    limitTextFieldToTwoNumbers(timeInHour);
    timeInHour.setPromptText("Hour: 0-23");
    TextField timeInMinute = new TextField();
    timeInMinute.setId("editDialogTimeInMinute");
    limitTextFieldToTwoNumbers(timeInMinute);
    timeInMinute.setPromptText("Min: 0-59");

    // Time out inputs
    final Label labelOut = new Label("Time Out");
    DatePicker dateOut = new DatePicker();
    dateOut.setId("editDialogDateOut");
    TextField timeOutHour = new TextField();
    timeOutHour.setId("editDialogTimeOutHour");
    limitTextFieldToTwoNumbers(timeOutHour);
    timeOutHour.setPromptText("Hour: 0-23");
    TextField timeOutMinute = new TextField();
    timeOutMinute.setId("editDialogTimeOutMinute");
    limitTextFieldToTwoNumbers(timeOutMinute);
    timeOutMinute.setPromptText("Min: 0-59");

    // Dialog
    dialog.getDialogPane().setContent(new VBox(8, labelIn, dateIn, timeInHour, timeInMinute,
        labelOut, dateOut, timeOutHour, timeOutMinute));

    // AutoFill
    LocalDateTime editingWorkdayTimeIn = workday.getTimeIn();
    dateIn.setValue(LocalDate.of(editingWorkdayTimeIn.getYear(),
        editingWorkdayTimeIn.getMonth(),
        editingWorkdayTimeIn.getDayOfMonth()));
    timeInHour.setText(String.valueOf(editingWorkdayTimeIn.getHour()));
    timeInMinute.setText(String.valueOf(editingWorkdayTimeIn.getMinute()));

    if (workday.isTimedOut()) {
      LocalDateTime editingWorkdayTimeOut = workday.getTimeOut();
      dateOut.setValue(LocalDate.of(editingWorkdayTimeOut.getYear(),
          editingWorkdayTimeOut.getMonth(),
          editingWorkdayTimeOut.getDayOfMonth()));
      timeOutHour.setText(String.valueOf(editingWorkdayTimeOut.getHour()));
      timeOutMinute.setText(String.valueOf(editingWorkdayTimeOut.getMinute()));
    }

    // Show interface
    try {
      Optional<ButtonType> choice = dialog.showAndWait();
      if (choice.isPresent()) {
        switch (choice.get().getButtonData()) {

          case APPLY:

            // Creating a main boolean variable to be able to show all validation-errors.
            boolean validationFailure = false;
            if (!isValidMinuteInput(timeInMinute.getText())) {
              warningDialog(timeInMinute.getText() + " is not a valid input for minute-in.");
              validationFailure = true;
            }
            if (!isValidMinuteInput(timeOutMinute.getText())) {
              warningDialog(timeOutMinute.getText() + " is not a valid input for minute-out.");
              validationFailure = true;
            }
            if (!isValidHourInput(timeInHour.getText())) {
              warningDialog(timeInHour.getText() + " is not a valid input for hour-in.");
              validationFailure = true;
            }
            if (!isValidHourInput(timeOutHour.getText())) {
              warningDialog(timeOutHour.getText() + " is not a valid input for hour-out.");
              validationFailure = true;
            }
            if (validationFailure) {
              openWorkdayEditInterface(workday);
              break;
            }

            // Get confirmation
            boolean result = confirmationDialog(
                "Are you sure you want to change the workday to these values?"
            );
          
            if (result) {

              int parsedTimeInHour = Integer.parseInt(timeInHour.getText());
              int parsedTimeInMinute = Integer.parseInt(timeInMinute.getText());
              int parsedTimeOutHour = Integer.parseInt(timeOutHour.getText());
              int parsedTimeOutMinute = Integer.parseInt(timeOutMinute.getText());

              // DateTime in
              LocalDate date = dateIn.getValue();
              LocalTime time = LocalTime.of(parsedTimeInHour, parsedTimeInMinute);
              LocalDateTime dateTimeIn = LocalDateTime.of(date, time);

              // DateTime out
              LocalDate date2 = dateOut.getValue();
              LocalTime time2 = LocalTime.of(parsedTimeOutHour, parsedTimeOutMinute);
              LocalDateTime dateTimeOut = LocalDateTime.of(date2, time2);

              saveWorkdayEditChoices(workday, dateTimeIn, dateTimeOut);

            } else {
              openWorkdayEditInterface(workday);
            }

            break;

          case OTHER:
            // Get confirmation
            boolean deleteConfirmation = confirmationDialog(
                "Are you sure you want to delete this workday?"
            );
            if (deleteConfirmation) {

              deleteWorkday(workday);

            } else {
              openWorkdayEditInterface(workday);
            }

            break;

          case CANCEL_CLOSE:
            System.out.println("Workday editing cancelled");
            break;

          default:
            System.err.println("The window wasn't closed properly");
            break;
        }
      } else {
        System.out.println("The window wasn't closed properly");
      }
    } catch (Exception e) {
      e.printStackTrace();
      displayError(e.getMessage());
      setApiStatus();
    }

  }

  private boolean isValidNumberInput(String input) {
    if (0 < input.length() && input.length() < 3) {
      return true;
    }
    return false;
  }

  private boolean isValidHourInput(String input) {
    if (!isValidNumberInput(input)) {
      return false;
    }
    int integer = Integer.parseInt(input);
    if (integer < 0 || 23 < integer) {
      return false;
    }
    return true;
  }

  private boolean isValidMinuteInput(String input) {
    if (!isValidNumberInput(input)) {
      return false;
    }

    int integer = Integer.parseInt(input);

    if (integer < 0 || 59 < integer) {
      return false;
    }

    return true;
  }

  private void saveWorkdayEditChoices(
      Workday workday, LocalDateTime timeIn, LocalDateTime timeOut) {
    try {
      timeMaster.editWorkday(workday, timeIn, timeOut);
    } catch (IllegalArgumentException e) {
      displayError(e.getMessage());
    } catch (IllegalStateException e) {
      displayError(e.getMessage());
    } catch (IOException e) {
      displayError(e.getMessage());
      setApiStatus();
    } catch (Exception e) {
      displayError(e.getMessage());
      setApiStatus();
      e.printStackTrace();
    }
    showWorkdayHistory();
    setEmployeeStatus();
  }

  private void deleteWorkday(Workday workday) {
    try {
      timeMaster.deleteWorkdayFromEmployee(workday);
      updateDisplay();
    } catch (IllegalArgumentException e) {
      displayError(e.getMessage());
    } catch (IllegalStateException e) {
      displayError(e.getMessage());
    } catch (Exception e) {
      displayError("Something went wrong!\n" + e.getMessage());
      e.printStackTrace();
      setApiStatus();
    }

  }

  @FXML
  private void handleDeleteEmployee() {
    boolean result = confirmationDialog(
      "Are you sure you want to delete the employee?"
    );

    if(result) {
      try {
        timeMaster.deleteChosenEmployee();
        updateDisplay();
        setDeleteStatus(true);
      } catch (IllegalStateException e) {
        displayError(e.getMessage());
        setDeleteStatus(false);
      } catch (IOException e) {
        displayError(e.getMessage());
        e.printStackTrace();
        setDeleteStatus(false);
        setApiStatus();
      } catch (Exception e) {
        displayError(e.getMessage());
        e.printStackTrace();
        setApiStatus();
      }
    }
  }

}
