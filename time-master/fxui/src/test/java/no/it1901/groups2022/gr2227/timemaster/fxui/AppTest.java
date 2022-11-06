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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.it1901.groups2022.gr2227.timemaster.core.Employee;

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
  String testName = "Test";
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

  public Parent getRootNode() {
    return root;
  }



  @Test
  public void testAppConstructor() {
    assertDoesNotThrow(() -> new App());
  }



  @Test
  public void testThatControllerIsPresent() {
    assertNotNull(this.controller);
  }



  @Test
  public void testApiStatusOffline() {
    assumeTrue((!controller.getApiStatus()) && (!controller.getIsUsingApi()));
    Text status = lookup("#statusTextApi").query();
    FxAssert.verifyThat(status, s -> s.getText().equals("Offline"));
    Circle indicator = lookup("#statusIndicatorApi").query();
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
  }



  @Test
  public void testApiStatusAvailable() {
    assumeTrue(controller.getApiStatus() && (!controller.getIsUsingApi()));
    Text status = lookup("#statusTextApi").query();
    FxAssert.verifyThat(status, s -> s.getText().equals("Available"));
    Circle indicator = lookup("#statusIndicatorApi").query();
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.BLUE));
  }



  @Test
  public void testApiStatusOnline() {
    assumeTrue(controller.getApiStatus());
    controller.setApplicationInProductionState();
    Text status = lookup("#statusTextApi").query();
    FxAssert.verifyThat(status, s -> s.getText().equals("Online"));
    Circle indicator = lookup("#statusIndicatorApi").query();
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
    // Add valid employee
    clickOn(LabeledMatchers.hasText("Add New Employee"));
    clickOn("#newEmployeeName").write(testName);
    clickOn("#addNewEmployeeButton");

    TextField nameInput = lookup("#newEmployeeName").query();
    FxAssert.verifyThat(nameInput, n -> n.getText().length() == 0);
    Text status = lookup("#addStatus").query();
    FxAssert.verifyThat(status, s -> s.getFill().equals(Color.GREEN));
    FxAssert.verifyThat(status, s -> s.getText().length() > 0);
    ListView<Employee> listView = lookup("#chooseEmployeeListView").query();
    ObservableList<Employee> employeesList = listView.getItems();
    assertTrue(employeesList.size() > 0);
    assertTrue(employeesList.get(0).getName().equals(testName));

    // Add invalid employee
    // clickOn("#newEmployeeName").write(testName);
    clickOn("#addNewEmployeeButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
  }



  @Test
  public void testAutoStampIn() {
    // Adding an employee
    clickOn(LabeledMatchers.hasText("Add New Employee"));
    clickOn("#newEmployeeName").write(testName);
    clickOn("#addNewEmployeeButton");
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#autoRegisterTimeButton");

    Text status = lookup("#statusText").query();
    FxAssert.verifyThat(status, s -> s.getText().equals("Active"));
    Circle indicator = lookup("#statusIndicator").query();
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GREEN));
  }



  @Test
  public void testManualStampIn() {
    // Adding an employee
    clickOn(LabeledMatchers.hasText("Add New Employee"));
    clickOn("#newEmployeeName").write(testName);
    clickOn("#addNewEmployeeButton");
    clickOn(LabeledMatchers.hasText("Stamp In"));
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#autoRegisterTimeButton");

    Text status = lookup("#statusText").query();
    FxAssert.verifyThat(status, s -> s.getText().equals("Active"));
    Circle indicator = lookup("#statusIndicator").query();
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GREEN));
  }





}
