package timeMaster;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class TimeMasterController {

    private Employee chosenEmployee;
    private TimeMasterFileHandler timeMasterFileHandler = new TimeMasterFileHandler(System.getProperty("user.dir") + "\\lagring\\");

    @FXML MenuButton chooseEmployee;
    @FXML DatePicker chooseDate;
    
    private ArrayList<Employee> employees = new ArrayList<>();

    @FXML void initialize(){ //Kalles når appen starter
        this.chooseDate.setValue(LocalDate.now());
        this.readEmployees();
        updateEmployeeMenu();
    }

    private void saveEmployees () {
        //Lagre alle ansatte
        this.timeMasterFileHandler.writeEmployees(this.employees);
    }

    private void readEmployees () {
        //Les inn alle ansatte
        this.employees = this.timeMasterFileHandler.readEmployees();

    }

    private void updateEmployeeMenu() {
        this.chooseEmployee.getItems().clear();
        for (int i = 0; i < this.employees.size(); i++){
            int employeeIndex = i;
            MenuItem menuItem = new MenuItem(this.employees.get(i).getName());
            menuItem.setOnAction((a)->{ // Ta ActionEventet "a" som input til lambda-uttrykket selv om vi ikke bruker det
                this.chosenEmployee = employees.get(employeeIndex); 
            });
        }

    }

}
