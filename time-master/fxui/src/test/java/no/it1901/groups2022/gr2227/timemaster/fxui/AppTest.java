package no.it1901.groups2022.gr2227.timemaster.fxui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.it1901.groups2022.gr2227.timemaster.core.Employee;
import no.it1901.groups2022.gr2227.timemaster.core.Workday;

import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;


/**
 * TestFX App test
 */
public class AppTest extends ApplicationTest {

  private Parent root;
  private TimeMasterController controller;
  String testName = "John Smith";
  String testName2 = "Elizabeth Swann";
  File file;

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("timeMaster.fxml"));
    root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    controller.setApplicationInTestState();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void init() {
    file = new File(Paths.get(System.getProperty("user.dir"), "../rest/timeMasterSaveFiles").toString(), "employeesTest.json");
    try {
      file.createNewFile();
    } catch (Exception e) {

    }
  }

  @AfterEach
  public void cleanUp() {
    file.delete();
  }



  // Private methods
  private void addNewEmployee(String name) {
    clickOn(LabeledMatchers.hasText("Manage Employee"));
    clickOn("#newEmployeeName").write(name);
    clickOn("#addNewEmployeeButton");
  }



  @Test
  public void testAppConstructor() {
    // Assert that controller is present
    assertNotNull(this.controller);

    // Temporarily creating a new App object
    assertDoesNotThrow(() -> new App());
  }



  @Test
  public void testApiStatusOffline() {
    assumeTrue((!controller.getApiStatus()) && (!controller.getIsUsingApi()));
    
    Text status = lookup("#statusTextApi").query();
    Circle indicator = lookup("#statusIndicatorApi").query();
    
    FxAssert.verifyThat(status, s -> s.getText().equals("Offline"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
  }



  @Test
  public void testApiStatusAvailable() {
    assumeTrue(controller.getApiStatus() && (!controller.getIsUsingApi()));
    
    Text status = lookup("#statusTextApi").query();
    Circle indicator = lookup("#statusIndicatorApi").query();
    
    FxAssert.verifyThat(status, s -> s.getText().equals("Available"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.BLUE));
  }



  @Test
  public void testApiStatusOnline() {
    assumeTrue(controller.getApiStatus());

    final Text status = lookup("#statusTextApi").query();
    final Circle indicator = lookup("#statusIndicatorApi").query();
    
    controller.setApplicationInProductionState();
    FxAssert.verifyThat(status, s -> s.getText().equals("Online"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GREEN));
  }



  @ParameterizedTest
  @MethodSource
  public void testTabs(String input) {
      assertDoesNotThrow(() -> clickOn(LabeledMatchers.hasText(input)));
  }

  private static Stream<String> testTabs() {
    return Stream.of("Stamp In", "Check Hours Worked", "Manage Employee");
  }



  @Test
  public void testAddEmployee() {
    final ListView<Employee> listView = lookup("#chooseEmployeeListView").query();
    final TextField nameInput = lookup("#newEmployeeName").query();
    final Text status = lookup("#addStatus").query();

    addNewEmployee(testName);

    // Input is empty
    FxAssert.verifyThat(nameInput, n -> n.getText().length() == 0);

    // Status updates and is green 
    FxAssert.verifyThat(status, s -> s.getFill().equals(Color.GREEN));
    FxAssert.verifyThat(status, s -> s.getText().length() > 0);

    // Listview updates with the name of the employee
    ObservableList<Employee> employeesList = listView.getItems();
    assertTrue(employeesList.size() > 0);
    assertTrue(employeesList.get(0).getName().equals(testName));

    // Add invalid employee
    clickOn("#addNewEmployeeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    clickOn("#newEmployeeName").write("Will@");
    clickOn("#addNewEmployeeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
  }



  @Test
  public void clickEmployee() {
    final ListView<Workday> listView = lookup("#workdayHistoryList").query();
    final Text stampInEmployeeName = lookup("#stampInEmployeeName").query();
    final Text historyEmployeeName = lookup("#historyEmployeeName").query();


    addNewEmployee(testName);


    // Before click
    FxAssert.verifyThat("#deleteEmployeeButton", NodeMatchers.isDisabled());
    FxAssert.verifyThat("#autoRegisterTimeButton", NodeMatchers.isDisabled());
    FxAssert.verifyThat("#registerTimeButton", NodeMatchers.isDisabled());
    FxAssert.verifyThat("#chooseDateButton", NodeMatchers.isDisabled());
    FxAssert.verifyThat("#inputHour", NodeMatchers.isDisabled());
    FxAssert.verifyThat("#inputMinutes", NodeMatchers.isDisabled());
    ObservableList<Workday> workdayList = listView.getItems();
    assertTrue(workdayList.size() == 0);

    clickOn(LabeledMatchers.hasText(testName));

    // After click
    FxAssert.verifyThat("#deleteEmployeeButton", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#autoRegisterTimeButton", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#registerTimeButton", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#chooseDateButton", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#inputHour", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#inputMinutes", NodeMatchers.isEnabled());
    FxAssert.verifyThat(stampInEmployeeName, s -> s.getText().equals(testName));    
    FxAssert.verifyThat(historyEmployeeName, h -> h.getText().equals(testName));
  }



  @Test
  public void testDeleteEmployee() {
    final ListView<Employee> employeeListView = lookup("#chooseEmployeeListView").query();
    final Text deleteStatus = lookup("#deleteStatus").query();
    
    addNewEmployee(testName);
    addNewEmployee(testName2);
    assertTrue(employeeListView.getItems().size() == 2);

    // Try to click when no employee is selected
    clickOn("#deleteEmployeeButton");
    assertTrue(employeeListView.getItems().size() == 2);
    FxAssert.verifyThat(deleteStatus, d -> d.getText().length() == 0);
    
    // Delete one
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#deleteEmployeeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    assertTrue(employeeListView.getItems().size() == 1);
    assertTrue(employeeListView.getItems().get(0).getName().equals(testName2));
    FxAssert.verifyThat(deleteStatus, d -> d.getText().length() > 0);
    FxAssert.verifyThat(deleteStatus, d -> d.getFill().equals(Color.GREEN));

    // Add a new and delete the other
    addNewEmployee(testName);
    assertTrue(employeeListView.getItems().size() == 2);
    clickOn(LabeledMatchers.hasText(testName2));
    FxAssert.verifyThat(deleteStatus, d -> d.getText().length() == 0);
    clickOn("#deleteEmployeeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    assertTrue(employeeListView.getItems().size() == 1);
    assertTrue(employeeListView.getItems().get(0).getName().equals(testName));
    FxAssert.verifyThat(deleteStatus, d -> d.getText().length() > 0);
    FxAssert.verifyThat(deleteStatus, d -> d.getFill().equals(Color.GREEN));

    // Cancel deletion
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#deleteEmployeeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("Cancel"));
    assertTrue(employeeListView.getItems().size() == 1);
    FxAssert.verifyThat(deleteStatus, d -> d.getText().length() == 0);
  }



  @Test
  public void testDeleteEmployeeWithNoChosenEmployee() {
    final Button button = lookup("#deleteEmployeeButton").query();
    final Text deleteStatus = lookup("#deleteStatus").query();
    
    clickOn(LabeledMatchers.hasText("Manage Employee"));
    button.setDisable(false);
    clickOn("#deleteEmployeeButton");
    // Confirmation
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    // Alert
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat(deleteStatus, d -> d.getText().length() > 0);
    FxAssert.verifyThat(deleteStatus, d -> d.getFill().equals(Color.RED));
  }



  @Test
  public void testAutoCheckInOut() {
    final Button autoRegisterTimeButton = lookup("#autoRegisterTimeButton").queryButton();
    final Button registerTimeButton = lookup("#registerTimeButton").queryButton();
    final Text status = lookup("#statusText").queryText();
    final Circle indicator = lookup("#statusIndicator").query();
    final Text clockInInfo = lookup("#clockInInfo").query();

    addNewEmployee(testName);
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName));

    // Before Check in
    FxAssert.verifyThat(autoRegisterTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(registerTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(status, s -> s.getText().equals("Off"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
    FxAssert.verifyThat(clockInInfo, c -> c.getText().length() == 0);

    // Check in
    clickOn("#autoRegisterTimeButton");
    FxAssert.verifyThat(autoRegisterTimeButton, b -> b.getText().equals("Check out"));
    FxAssert.verifyThat(registerTimeButton, b -> b.getText().equals("Check out"));
    FxAssert.verifyThat(status, s -> s.getText().equals("Active"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GREEN));
    FxAssert.verifyThat(clockInInfo, c -> c.getText().length() > 0);

    // Add another employee to check that the display updates
    addNewEmployee(testName2);
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName2));
    FxAssert.verifyThat(autoRegisterTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(registerTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(status, s -> s.getText().equals("Off"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
    FxAssert.verifyThat(clockInInfo, c -> c.getText().length() == 0);

    // Check out employee1
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#autoRegisterTimeButton");
    FxAssert.verifyThat(autoRegisterTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(registerTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(status, s -> s.getText().equals("Off"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
    FxAssert.verifyThat(clockInInfo, c -> c.getText().length() == 0);
  }



  @Test
  public void testManualCheckInOut() {
    final Button autoRegisterTimeButton = lookup("#autoRegisterTimeButton").queryButton();
    final Button registerTimeButton = lookup("#registerTimeButton").queryButton();
    final TextField hourField = lookup("#inputHour").query();
    final TextField minuteField = lookup("#inputMinutes").query();
    final Text status = lookup("#statusText").query();
    final Circle indicator = lookup("#statusIndicator").query();
    final Text clockInInfo = lookup("#clockInInfo").query();
    final DatePicker datePicker = lookup("#chooseDateButton").query();

    addNewEmployee(testName);
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName));

    // Before check in
    FxAssert.verifyThat(registerTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(autoRegisterTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(status, s -> s.getText().equals("Off"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
    FxAssert.verifyThat(clockInInfo, c -> c.getText().length() == 0);
    FxAssert.verifyThat(hourField, h -> h.getText().length() == 0);
    FxAssert.verifyThat(minuteField, m -> m.getText().length() == 0);

    // Check in
    clickOn("#inputHour").write("01");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    FxAssert.verifyThat(registerTimeButton, b -> b.getText().equals("Check out"));
    FxAssert.verifyThat(autoRegisterTimeButton, b -> b.getText().equals("Check out"));
    FxAssert.verifyThat(status, s -> s.getText().equals("Active"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GREEN));
    FxAssert.verifyThat(clockInInfo, c -> c.getText().length() > 0);
    FxAssert.verifyThat(hourField, h -> h.getText().length() == 0);
    FxAssert.verifyThat(minuteField, m -> m.getText().length() == 0);

    // Add another employee to check that the display updates
    addNewEmployee(testName2);
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName2));
    FxAssert.verifyThat(autoRegisterTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(registerTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(status, s -> s.getText().equals("Off"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
    FxAssert.verifyThat(clockInInfo, c -> c.getText().length() == 0);

    // Check out
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#inputHour").write("02");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    FxAssert.verifyThat(registerTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(autoRegisterTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(status, s -> s.getText().equals("Off"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
    FxAssert.verifyThat(clockInInfo, c -> c.getText().length() == 0);
    FxAssert.verifyThat(hourField, h -> h.getText().length() == 0);
    FxAssert.verifyThat(minuteField, m -> m.getText().length() == 0);
    
    // Add same time but different date
    datePicker.setValue(datePicker.getValue().plusDays(1));
    clickOn("#inputHour").write("01");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("02");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");

    // No hour-input
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat(minuteField, m -> m.getText().equals("00"));

    // Reset
    minuteField.setText("");
    
    // No minute-input
    clickOn("#inputHour").write("00");
    clickOn("#registerTimeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat(hourField, h -> h.getText().equals("00"));

    // Reset
    hourField.setText("");

    // No input (will generate two warningdialogues)
    clickOn("#registerTimeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));

    // Invalid minute-input
    clickOn("#inputHour").write("23");
    clickOn("#inputMinutes").write("60");
    clickOn("#registerTimeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));

    // Reset
    hourField.setText("");
    minuteField.setText("");
    
    // Invalid hour-input
    clickOn("#inputHour").write("24");
    clickOn("#inputMinutes").write("59");
    clickOn("#registerTimeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));

    // Reset
    hourField.setText("");
    minuteField.setText("");

    // Too long minute-input
    clickOn("#inputMinutes").write("599");
    FxAssert.verifyThat(minuteField, h -> h.getText().equals("59"));

    // Reset
    minuteField.setText("");
    
    // Too long hour-input
    clickOn("#inputHour").write("234");
    FxAssert.verifyThat(hourField, h -> h.getText().equals("23"));

    // Reset
    hourField.setText("");

    // Not allowing string in minute-input
    clickOn("#inputMinutes").write("-a");
    FxAssert.verifyThat(minuteField, h -> h.getText().equals(""));

    // Reset
    minuteField.setText("");
    
    // Not allowing string in hour-input
    clickOn("#inputHour").write("-b");
    FxAssert.verifyThat(hourField, h -> h.getText().equals(""));

    // Reset
    hourField.setText("");

    // Conflicting check-in timestamp
    clickOn("#inputHour").write("01");
    clickOn("#inputMinutes").write("30");
    clickOn("#registerTimeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat(hourField, h -> h.getText().equals("01"));
    FxAssert.verifyThat(minuteField, m -> m.getText().equals("30"));

    // Reset
    hourField.setText("");
    minuteField.setText("");

    // Cannot start an open workday earlier than any workday
    clickOn("#inputHour").write("00");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat(hourField, h -> h.getText().equals("00"));
    FxAssert.verifyThat(minuteField, m -> m.getText().equals("00"));

    // Reset
    hourField.setText("");
    minuteField.setText("");

    // Conflicting check-out timestamp before check-in timestamp
    clickOn("#inputHour").write("03");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("02");
    clickOn("#inputMinutes").write("30");
    clickOn("#registerTimeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat(hourField, h -> h.getText().equals("02"));
    FxAssert.verifyThat(minuteField, m -> m.getText().equals("30"));

  }



  @Test
  public void testCheckInOutWithNoEmployeeSet() {
    final Button autoRegisterTimeButton = lookup("#autoRegisterTimeButton").queryButton();
    final Button registerTimeButton = lookup("#registerTimeButton").queryButton();

    clickOn(LabeledMatchers.hasText("Stamp In"));

    // Auto check in
    autoRegisterTimeButton.getParent().setDisable(false);
    clickOn("#autoRegisterTimeButton");

    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));

    // Manual check in
    registerTimeButton.getParent().setDisable(false);
    clickOn("#inputHour").write("00");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");

    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
  }



  @Test
  public void testWorkdayRegistration() {
    final ListView<Workday> workdaysListView = lookup("#workdayHistoryList").query();
    final DatePicker datePicker = lookup("#chooseDateButton").query();
    final LocalDate date = datePicker.getValue();

    // Initialize employees
    addNewEmployee(testName);
    addNewEmployee(testName2);
    clickOn(LabeledMatchers.hasText("Stamp In"));

    // DateString
    String dayOfWeek = date.getDayOfWeek().toString();
    String dayOfMonth = String.valueOf(date.getDayOfMonth());
    String month = date.getMonth().toString();
    String year = String.valueOf(date.getYear());
    String dateString = dayOfWeek + " " + dayOfMonth + " " + month + " " + year;

    // Workdaylist employee1
    clickOn(LabeledMatchers.hasText(testName));
    assertTrue(workdaysListView.getItems().size() == 0);

    // Workdaylist employee2
    clickOn(LabeledMatchers.hasText(testName2));
    assertTrue(workdaysListView.getItems().size() == 0);

    // Check in employee2
    clickOn("#inputHour").write("01");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    assertTrue(workdaysListView.getItems().size() == 1);
    Workday workday = workdaysListView.getItems().get(0);
    assertTrue(workday.toString().contains(dateString));
    assertTrue(workday.toString().contains("01:00"));

    // Workdaylist employee1
    clickOn(LabeledMatchers.hasText(testName));
    assertTrue(workdaysListView.getItems().size() == 0);

    // Check out employee2
    clickOn(LabeledMatchers.hasText(testName2));
    clickOn("#inputHour").write("02");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    assertTrue(workdaysListView.getItems().size() == 1);
    Workday workday1p2 = workdaysListView.getItems().get(0);
    assertTrue(workday1p2.toString().contains(dateString));
    assertTrue(workday1p2.toString().contains("02:00"));

    // Workdaylist employee1
    clickOn(LabeledMatchers.hasText(testName));
    assertTrue(workdaysListView.getItems().size() == 0);

    // Check in and out employee2
    clickOn(LabeledMatchers.hasText(testName2));
    clickOn("#inputHour").write("03");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("04");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    assertTrue(workdaysListView.getItems().size() == 2);
    // Assert that new newest element is the first element in the list
    Workday workday1p3 = workdaysListView.getItems().get(0);
    assertTrue(workday1p3.toString().contains("03:00"));
    assertTrue(workday1p3.toString().contains("04:00"));

    // Workdaylist employee1
    clickOn(LabeledMatchers.hasText(testName));
    assertTrue(workdaysListView.getItems().size() == 0);

    // Check in and out employee1 in the same timeperiod
    clickOn("#inputHour").write("01");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("02");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    assertTrue(workdaysListView.getItems().size() == 1);

    // Workdaylist employee2
    clickOn(LabeledMatchers.hasText(testName2));
    assertTrue(workdaysListView.getItems().size() == 2);
  }



  @Test
  public void testEditWorkday() {
    final Button autoRegisterTimeButton = lookup("#autoRegisterTimeButton").queryButton();
    final Button registerTimeButton = lookup("#registerTimeButton").queryButton();
    final Text status = lookup("#statusText").queryText();
    final Circle indicator = lookup("#statusIndicator").query();
    final Text clockInInfo = lookup("#clockInInfo").query();
    final ListView<Workday> workdaysListView = lookup("#workdayHistoryList").query();
    DatePicker dateIn;
    TextField timeInHour;
    TextField timeInMinute;
    DatePicker dateOut;
    TextField timeOutHour;
    TextField timeOutMinute;



    // Initialize employee with two workdays
    addNewEmployee(testName);
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName));
    // Workday 1
    clickOn("#inputHour").write("01");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("02");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    // Workday 2
    clickOn("#inputHour").write("04");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("05");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    assertTrue(workdaysListView.getItems().size() == 2);
    // Workdays
    Workday workday1 = workdaysListView.getItems().get(0);
    Workday workday2 = workdaysListView.getItems().get(1);



    // Open workday 1
    clickOn(LabeledMatchers.hasText("Check Hours Worked"));
    clickOn(LabeledMatchers.hasText(workday1.toString()));
    // Verify that all elements are visible
    FxAssert.verifyThat("#editDialogDateIn", NodeMatchers.isVisible());
    FxAssert.verifyThat("#editDialogTimeInHour", NodeMatchers.isVisible());
    FxAssert.verifyThat("#editDialogTimeInMinute", NodeMatchers.isVisible());
    FxAssert.verifyThat("#editDialogDateOut", NodeMatchers.isVisible());
    FxAssert.verifyThat("#editDialogTimeOutHour", NodeMatchers.isVisible());
    FxAssert.verifyThat("#editDialogTimeOutMinute", NodeMatchers.isVisible());
    FxAssert.verifyThat("Ok", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    FxAssert.verifyThat("Delete", NodeMatchers.isVisible());
    // Get autofill
    dateIn = lookup("#editDialogDateIn").query();
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    dateOut = lookup("#editDialogDateOut").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    assertTrue(dateIn.getValue().equals(workday1.getTimeIn().toLocalDate()));
    assertTrue(timeInHour.getText().equals(Integer.toString(workday1.getTimeIn().getHour())));
    assertTrue(timeInMinute.getText().equals(Integer.toString(workday1.getTimeIn().getMinute())));
    assertTrue(dateOut.getValue().equals(workday1.getTimeOut().toLocalDate()));
    assertTrue(timeOutHour.getText().equals(Integer.toString(workday1.getTimeOut().getHour())));
    assertTrue(timeOutMinute.getText().equals(Integer.toString(workday1.getTimeOut().getMinute())));

    // Cancel edit
    clickOn(LabeledMatchers.hasText("Cancel"));
    assertTrue(workdaysListView.getItems().get(0).equals(workday1));




    // Do changes but cancel
    clickOn(LabeledMatchers.hasText(workday1.toString()));
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    // Clear Inputs
    clickOn("#editDialogTimeInHour").eraseText(2);
    clickOn("#editDialogTimeInMinute").eraseText(2);
    clickOn("#editDialogTimeOutHour").eraseText(2);
    clickOn("#editDialogTimeOutMinute").eraseText(2);
    // Add values
    clickOn("#editDialogTimeInHour").write("12");
    clickOn("#editDialogTimeInMinute").write("00");
    clickOn("#editDialogTimeOutHour").write("13");
    clickOn("#editDialogTimeOutMinute").write("00");
    // Cancel confirmation
    clickOn(LabeledMatchers.hasText("Ok"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("Cancel"));
    // Assert that the values reset
    // Need to retrive these again since the window is new
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    assertTrue(dateIn.getValue().equals(workday1.getTimeIn().toLocalDate()));
    assertTrue(timeInHour.getText().equals(Integer.toString(workday1.getTimeIn().getHour())));
    assertTrue(timeInMinute.getText().equals(Integer.toString(workday1.getTimeIn().getMinute())));
    assertTrue(dateOut.getValue().equals(workday1.getTimeOut().toLocalDate()));
    assertTrue(timeOutHour.getText().equals(Integer.toString(workday1.getTimeOut().getHour())));
    assertTrue(timeOutMinute.getText().equals(Integer.toString(workday1.getTimeOut().getMinute())));
    // Exit
    clickOn(LabeledMatchers.hasText("Cancel"));



    // Test invalid input
    clickOn(LabeledMatchers.hasText(workday1.toString()));
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    //Clear Inputs
    clickOn("#editDialogTimeInHour").eraseText(2);
    clickOn("#editDialogTimeInMinute").eraseText(2);
    clickOn("#editDialogTimeOutHour").eraseText(2);
    clickOn("#editDialogTimeOutMinute").eraseText(2);
    // Too long
    clickOn("#editDialogTimeInHour").write("234");
    FxAssert.verifyThat(timeInHour, h -> h.getText().equals("23"));
    clickOn("#editDialogTimeInMinute").write("234");
    FxAssert.verifyThat(timeInMinute, h -> h.getText().equals("23"));
    clickOn("#editDialogTimeOutHour").write("234");
    FxAssert.verifyThat(timeOutHour, h -> h.getText().equals("23"));
    clickOn("#editDialogTimeOutMinute").write("234");
    FxAssert.verifyThat(timeOutMinute, h -> h.getText().equals("23"));
    //Clear Inputs
    clickOn("#editDialogTimeInHour").eraseText(2);
    clickOn("#editDialogTimeInMinute").eraseText(2);
    clickOn("#editDialogTimeOutHour").eraseText(2);
    clickOn("#editDialogTimeOutMinute").eraseText(2);
    // Strings
    clickOn("#editDialogTimeInHour").write("-a");
    FxAssert.verifyThat(timeInHour, h -> h.getText().equals(""));
    clickOn("#editDialogTimeInMinute").write("-a");
    FxAssert.verifyThat(timeInMinute, h -> h.getText().equals(""));
    clickOn("#editDialogTimeOutHour").write("-a");
    FxAssert.verifyThat(timeOutHour, h -> h.getText().equals(""));
    clickOn("#editDialogTimeOutMinute").write("-a");
    FxAssert.verifyThat(timeOutMinute, h -> h.getText().equals(""));
    // Too big numbers
    clickOn("#editDialogTimeInHour").write("24");
    clickOn("#editDialogTimeInMinute").write("60");
    clickOn("#editDialogTimeOutHour").write("24");
    clickOn("#editDialogTimeOutMinute").write("60");
    clickOn(LabeledMatchers.hasText("Ok"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    assertTrue(workdaysListView.getItems().get(0).equals(workday1));
    // Empty inputs
    // Need to retrive these again since the window is new
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    //Clear Inputs
    clickOn("#editDialogTimeInHour").eraseText(2);
    clickOn("#editDialogTimeInMinute").eraseText(2);
    clickOn("#editDialogTimeOutHour").eraseText(2);
    clickOn("#editDialogTimeOutMinute").eraseText(2);
    clickOn(LabeledMatchers.hasText("Ok"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    assertTrue(workdaysListView.getItems().get(0).equals(workday1));
    // Close
    clickOn(LabeledMatchers.hasText("Cancel"));



    // Test valid edit
    clickOn(LabeledMatchers.hasText(workday2.toString()));
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    //Clear Inputs
    clickOn("#editDialogTimeInHour").eraseText(2);
    clickOn("#editDialogTimeInMinute").eraseText(2);
    clickOn("#editDialogTimeOutHour").eraseText(2);
    clickOn("#editDialogTimeOutMinute").eraseText(2);
    // Add values
    clickOn("#editDialogTimeInHour").write("06");
    clickOn("#editDialogTimeInMinute").write("00");
    clickOn("#editDialogTimeOutHour").write("07");
    clickOn("#editDialogTimeOutMinute").write("00");
    // Confirm
    clickOn(LabeledMatchers.hasText("Ok"));
    clickOn(LabeledMatchers.hasText("OK"));
    // Assertions
    Workday workday2p2 = workdaysListView.getItems().get(0);
    assertFalse(workday2p2.equals(workday2));
    assertTrue(workday2p2.toString().contains("06:00"));
    assertTrue(workday2p2.toString().contains("07:00"));



    // Test valid edit, shrink workday
    clickOn(LabeledMatchers.hasText(workday1.toString()));
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    //Clear Inputs
    clickOn("#editDialogTimeInHour").eraseText(2);
    clickOn("#editDialogTimeInMinute").eraseText(2);
    clickOn("#editDialogTimeOutHour").eraseText(2);
    clickOn("#editDialogTimeOutMinute").eraseText(2);
    // Add values
    clickOn("#editDialogTimeInHour").write("01");
    clickOn("#editDialogTimeInMinute").write("15");
    clickOn("#editDialogTimeOutHour").write("01");
    clickOn("#editDialogTimeOutMinute").write("45");
    // Confirm
    clickOn(LabeledMatchers.hasText("Ok"));
    clickOn(LabeledMatchers.hasText("OK"));
    // Assertions
    Workday workday1p2 = workdaysListView.getItems().get(1);
    assertFalse(workday1p2.equals(workday2));
    assertTrue(workday1p2.toString().contains("01:15"));
    assertTrue(workday1p2.toString().contains("01:45"));



    // Test invalid edit, conflicting timeout
    clickOn(LabeledMatchers.hasText(workday1p2.toString()));
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    //Clear Inputs
    clickOn("#editDialogTimeOutHour").eraseText(2);
    clickOn("#editDialogTimeOutMinute").eraseText(2);
    // Add values
    clickOn("#editDialogTimeOutHour").write("06");
    clickOn("#editDialogTimeOutMinute").write("30");
    // Confirm
    clickOn(LabeledMatchers.hasText("Ok"));
    clickOn(LabeledMatchers.hasText("OK"));
    // Confirm Error
    clickOn(LabeledMatchers.hasText("OK"));
    // Assertions
    Workday workday1p3 = workdaysListView.getItems().get(1);
    assertTrue(workday1p2.toString().equals(workday1p3.toString()));
 


    // Test invalid edit, conflicting timein
    clickOn(LabeledMatchers.hasText(workday1p3.toString()));
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    //Clear Inputs
    clickOn("#editDialogTimeInHour").eraseText(2);
    clickOn("#editDialogTimeInMinute").eraseText(2);
    clickOn("#editDialogTimeOutHour").eraseText(2);
    clickOn("#editDialogTimeOutMinute").eraseText(2);
    // Add values
    clickOn("#editDialogTimeInHour").write("06");
    clickOn("#editDialogTimeInMinute").write("30");
    clickOn("#editDialogTimeOutHour").write("07");
    clickOn("#editDialogTimeOutMinute").write("30");
    // Confirm
    clickOn(LabeledMatchers.hasText("Ok"));
    clickOn(LabeledMatchers.hasText("OK"));
    // Confirm Error
    clickOn(LabeledMatchers.hasText("OK"));
    // Assertions
    Workday workday1p4 = workdaysListView.getItems().get(1);
    assertTrue(workday1p3.toString().equals(workday1p4.toString()));



    // Test invalid edit, overlapping
    clickOn(LabeledMatchers.hasText(workday1p3.toString()));
    timeInHour = lookup("#editDialogTimeInHour").query();
    timeInMinute = lookup("#editDialogTimeInMinute").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    //Clear Inputs
    clickOn("#editDialogTimeInHour").eraseText(2);
    clickOn("#editDialogTimeInMinute").eraseText(2);
    clickOn("#editDialogTimeOutHour").eraseText(2);
    clickOn("#editDialogTimeOutMinute").eraseText(2);
    // Add values
    clickOn("#editDialogTimeInHour").write("05");
    clickOn("#editDialogTimeInMinute").write("30");
    clickOn("#editDialogTimeOutHour").write("07");
    clickOn("#editDialogTimeOutMinute").write("30");
    // Confirm
    clickOn(LabeledMatchers.hasText("Ok"));
    clickOn(LabeledMatchers.hasText("OK"));
    // Confirm Error
    clickOn(LabeledMatchers.hasText("OK"));
    // Assertions
    Workday workday1p5 = workdaysListView.getItems().get(1);
    assertTrue(workday1p4.toString().equals(workday1p5.toString()));
    


    // Open and edit an open workday and check that the UI updates
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#inputHour").write("12");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn(LabeledMatchers.hasText("Check Hours Worked"));
    Workday workday3 = workdaysListView.getItems().get(0);
    clickOn(LabeledMatchers.hasText(workday3.toString()));
    dateOut = lookup("#editDialogDateOut").query();
    timeOutHour = lookup("#editDialogTimeOutHour").query();
    timeOutMinute = lookup("#editDialogTimeOutMinute").query();
    dateOut.setValue(LocalDate.now());
    timeOutHour.setText("13");
    timeOutMinute.setText("00");
    clickOn(LabeledMatchers.hasText("Ok"));
    clickOn(LabeledMatchers.hasText("OK"));
    FxAssert.verifyThat(autoRegisterTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(registerTimeButton, b -> b.getText().equals("Check in"));
    FxAssert.verifyThat(status, s -> s.getText().equals("Off"));
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
    FxAssert.verifyThat(clockInInfo, c -> c.getText().length() == 0);
  }



  @Test
  public void testDeleteWorkday() {
    final ListView<Workday> workdaysListView = lookup("#workdayHistoryList").query();

    // Initialize employee with workday
    addNewEmployee(testName);
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#inputHour").write("01");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("02");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    assertTrue(workdaysListView.getItems().size() == 1);

    // Go to tab
    clickOn(LabeledMatchers.hasText("Check Hours Worked"));

    // Get first workday
    Workday workday = workdaysListView.getItems().get(0);

    // Open workday and cancel
    clickOn(LabeledMatchers.hasText(workday.toString()));
    FxAssert.verifyThat("Ok", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    FxAssert.verifyThat("Delete", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("Cancel"));
    assertTrue(workdaysListView.getItems().size() == 1);

    // Open workday and cancel delete
    clickOn(LabeledMatchers.hasText(workday.toString()));
    FxAssert.verifyThat("Ok", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    FxAssert.verifyThat("Delete", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("Delete"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("Cancel"));
    FxAssert.verifyThat("Ok", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    FxAssert.verifyThat("Delete", NodeMatchers.isVisible());
    assertTrue(workdaysListView.getItems().size() == 1);
    clickOn(LabeledMatchers.hasText("Cancel"));

    // Delete workday
    clickOn(LabeledMatchers.hasText(workday.toString()));
    FxAssert.verifyThat("Ok", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    FxAssert.verifyThat("Delete", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("Delete"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    assertTrue(workdaysListView.getItems().size() == 0);

    // Make sure the workday can be added again
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn("#inputHour").write("01");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("02");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    assertTrue(workdaysListView.getItems().size() == 1);

    // Add another workday
    clickOn("#inputHour").write("04");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("05");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    assertTrue(workdaysListView.getItems().size() == 2);

    // Get the workdays
    Workday workday2 = workdaysListView.getItems().get(0);
    Workday workday3 = workdaysListView.getItems().get(1);

    // Go to tab
    clickOn(LabeledMatchers.hasText("Check Hours Worked"));

    // Delete workday2
    clickOn(LabeledMatchers.hasText(workday2.toString()));
    FxAssert.verifyThat("Ok", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    FxAssert.verifyThat("Delete", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("Delete"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    // Check that workday3 still exists
    assertTrue(workdaysListView.getItems().size() == 1);
    assertTrue(workdaysListView.getItems().get(0).equals(workday3));
    assertTrue(workdaysListView.getItems().get(0).equals(workday3));

    // Add another employee with similar workday
    addNewEmployee(testName2);
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName2));
    clickOn("#inputHour").write("04");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    clickOn("#inputHour").write("05");
    clickOn("#inputMinutes").write("00");
    clickOn("#registerTimeButton");
    assertTrue(workdaysListView.getItems().size() == 1);

    // Delete the similar workday from employee1
    clickOn(LabeledMatchers.hasText(testName));
    clickOn(LabeledMatchers.hasText("Check Hours Worked"));
    clickOn(LabeledMatchers.hasText(workday3.toString()));
    FxAssert.verifyThat("Ok", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    FxAssert.verifyThat("Delete", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("Delete"));
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    FxAssert.verifyThat("Cancel", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    assertTrue(workdaysListView.getItems().size() == 0);

    // Check that the workday still exists at employee2
    clickOn(LabeledMatchers.hasText(testName2));
    assertTrue(workdaysListView.getItems().size() == 1);
  }

}
