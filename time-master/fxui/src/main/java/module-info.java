module no.it1901.groups2022.gr2227.timemaster.fxui {
    requires no.it1901.groups2022.gr2227.timemaster.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens no.it1901.groups2022.gr2227.timemaster.fxui to javafx.fxml, javafx.graphics;
    exports no.it1901.groups2022.gr2227.timemaster.fxui;
}
