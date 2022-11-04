package no.it1901.groups2022.gr2227.timemaster.fxui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    clickOn(LabeledMatchers.hasText("Add New Employee"));
    clickOn("#newEmployeeName").write(testName);
    clickOn("#addNewEmployeeButton");
    clickOn(LabeledMatchers.hasText("Stamp In"));

    FxAssert.verifyThat(testName, LabeledMatchers.hasText(testName));
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
  public void testAPIStatus() {
    // assumeTrue();
    Text status = lookup("#statusTextAPI").query();
    FxAssert.verifyThat(status, s -> s.getText().equals("Offline"));
    Circle indicator = lookup("#statusIndicatorAPI").query();
    FxAssert.verifyThat(indicator, i -> i.getFill().equals(Color.GRAY));
  }

}
