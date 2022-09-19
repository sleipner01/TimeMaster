package timeMaster;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class TimeMasterController {

    private Employee chosenEmployee;
    private Workday chosenWorkday;
    private Path saveDirPath = Paths.get(System.getProperty("user.dir"), "lagring");
    private TimeMasterFileHandler timeMasterFileHandler = new TimeMasterFileHandler(saveDirPath);

    @FXML MenuButton chooseEmployee;
    @FXML DatePicker chooseDate;
    @FXML TextField inputHr, inputMin;

    
    private ArrayList<Employee> employees = new ArrayList<>();

    @FXML void initialize(){ //Kalles når appen starter
        this.chooseDate.setValue(LocalDate.now());
        this.readEmployees();
        this.updateEmployeeMenu();
    }

    @FXML void handleRegisterTime() {
        try {
            LocalDate date = chooseDate.getValue();
            LocalTime chosenTime = LocalTime.of(Integer.parseInt(this.inputHr.getText()), Integer.parseInt(this.inputMin.getText()));
            if (this.chosenEmployee.getWorkdays().stream().anyMatch(e -> e.getDate().equals(date))) {
                this.chosenWorkday = this.chosenEmployee.getDate(date);
                this.chosenWorkday.setTimeOut(chosenTime); 
                this.saveEmployees();
            }
            else {
                this.chosenEmployee.addWorkday(new Workday(date, chosenTime)); 
                this.saveEmployees();
            }
            this.inputHr.clear();
            this.inputMin.clear();
        } catch (Exception e) {
            // TODO: handle exception
        }
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
                this.chooseEmployee.setText(this.chosenEmployee.getName());
            });
            this.chooseEmployee.getItems().add(menuItem); //Legger til i ansattmenyen
        }
    }



}
