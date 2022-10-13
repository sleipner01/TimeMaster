package timeMaster.fxui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


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
  
  public static void main(String[] args) {
    launch();
  }
}
