package timeMaster.fxui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.testfx.framework.junit5.ApplicationTest;

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
    public void test() {
        assertTrue(true);
    }

}

