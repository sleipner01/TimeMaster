module timeMaster.fxui {
    requires timeMaster.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens timeMaster.fxui to javafx.fxml, javafx.graphics;
    exports timeMaster.fxui;
}
