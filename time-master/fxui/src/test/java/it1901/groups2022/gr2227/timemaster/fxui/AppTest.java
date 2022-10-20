package it1901.groups2022.gr2227.timemaster.fxui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;


/**
 * TestFX App test
 */
public class AppTest extends ApplicationTest {

  private Parent root;
  private TimeMasterController controller;

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("timeMaster.fxml"));
    root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public Parent getRootNode() {
    return root;
  }

  @Test
  public void testThatControllerIsPresent() {
    assertNotNull(this.controller);
  }

  @ParameterizedTest
  @MethodSource
  public void testTabs(String input) {
      clickOn(LabeledMatchers.hasText(input));
  }

  private static Stream<String> testTabs() {
      return Stream.of("Stamp In", "Check Hours Worked", "Add New Employee");
  }

  @Test
  public void testAddEmployee() {
    String testName = "Test";
    clickOn(LabeledMatchers.hasText("Add New Employee"));
    clickOn("#newEmployeeName").write(testName);
    clickOn("#addNewEmployeeButton");
    clickOn(LabeledMatchers.hasText("Stamp In"));

    ArrayList<String> names = getEmployees();
    assertTrue(names.contains(testName));
  }

  private ArrayList<String> getEmployees() {
    MenuButton employeesButton = lookup("#chooseEmployeeButton").query();
    ObservableList<MenuItem> employees = employeesButton.getItems();
    
    ArrayList<String> employeeNames = new ArrayList<>();
    for (MenuItem employee : employees) {
      employeeNames.add(employee.getText());
    }
    return employeeNames;
  }

  @Test
  public void testAutoStampIn() {
    // Adding an employee
    String testName = "Test2";
    clickOn(LabeledMatchers.hasText("Add New Employee"));
    clickOn("#newEmployeeName").write(testName);
    clickOn("#addNewEmployeeButton");
    clickOn(LabeledMatchers.hasText("Stamp In"));

    clickOn("#newEmployeeName").write(testName);
    clickOn("#chooseEmployeeButton");
    clickOn(LabeledMatchers.hasText(testName));
    clickOn("#autoRegisterTimeButton");

    Text status = lookup("#statusText").query();
    FxAssert.verifyThat(status, s -> s.getText().equals("Active"));
  }







}
