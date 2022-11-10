/**
 * UI Module. Contains files for UI layout and controlling the application and visual elements.
 *
 * @author Magnus Byrkjeland
 * @since 1.0
 * @version 1.0
 */
module no.it1901.groups2022.gr2227.timemaster.fxui {
    requires no.it1901.groups2022.gr2227.timemaster.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens no.it1901.groups2022.gr2227.timemaster.fxui to javafx.fxml, javafx.graphics;
    exports no.it1901.groups2022.gr2227.timemaster.fxui;
}
