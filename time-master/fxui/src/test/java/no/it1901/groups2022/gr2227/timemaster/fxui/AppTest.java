package no.it1901.groups2022.gr2227.timemaster.fxui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
    clickOn(LabeledMatchers.hasText("Add New Employee"));
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
      return Stream.of("Stamp In", "Check Hours Worked", "Add New Employee");
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
    
    addNewEmployee(testName);
    addNewEmployee(testName2);
    assertTrue(employeeListView.getItems().size() == 2);
    // Try to click when no employee is selected
    clickOn("#deleteEmployeeButton");
    assertTrue(employeeListView.getItems().size() == 2);
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#deleteEmployeeButton");
    // TODO: Confirmation to come here
    assertTrue(employeeListView.getItems().size() == 1);
    assertTrue(employeeListView.getItems().get(0).getName().equals(testName2));
    addNewEmployee(testName);
    assertTrue(employeeListView.getItems().size() == 2);
    clickOn(LabeledMatchers.hasText(testName2));
    clickOn("#deleteEmployeeButton");
    // TODO: Confirmation to come here
    assertTrue(employeeListView.getItems().size() == 1);
    assertTrue(employeeListView.getItems().get(0).getName().equals(testName));
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

    // Check out
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

    // Check out
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


  
}
