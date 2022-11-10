package no.it1901.groups2022.gr2227.timemaster.fxui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App.java is the start class for the TimeMaster application.
 * It will initialize the user interface and connect to the controller.
 *
 * @version %I%, %G%
 * @since 1.0 
 * @see TimeMasterController
 */
public class App extends Application {
  
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("timeMaster.fxml"));
    Parent parent = fxmlLoader.load();
    Scene scene = new Scene(parent);
    scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
  }
  
  /**
   * Start method
   * 
   * @param args
   */
  public static void main(String[] args) {
    launch();
  }
}
