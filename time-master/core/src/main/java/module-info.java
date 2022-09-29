module timeMaster {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens timeMaster to javafx.fxml, javafx.graphics;
    exports timeMaster;
}
