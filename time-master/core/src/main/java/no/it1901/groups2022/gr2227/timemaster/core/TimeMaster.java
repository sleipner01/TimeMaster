package no.it1901.groups2022.gr2227.timemaster.core;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class TimeMaster {

  private Employee chosenEmployee;
  private ArrayList<Employee> employees;
  private ApiHandler apiHandler;
  private State state;

  public TimeMaster() {
    this.employees = new ArrayList<Employee>();
    this.apiHandler = new ApiHandler();

    // TODO: Check API status
    this.setApplicationInProductionState();
  }

  public TimeMaster(boolean test) {
    this.employees = new ArrayList<Employee>();
    this.apiHandler = new ApiHandler();

    this.setApplicationInTestState();
  }

  /**
   * For testing purposes. Turns off API calls from TimeMaster.
   */
  public void setApplicationInTestState() {
    this.state = State.TEST;
    this.employees.clear();
  }

  /**
   * Setting application in production mode to enable API.
   * Also useful to reset TimeMaster state to production
   * if needed after testing.
   */
  public void setApplicationInProductionState() {
    this.state = State.PRODUCTION;
  }

  public LocalDate getCurrentDate() {
    return LocalDate.now();
  }

  public LocalTime getCurrentTime() {
    return LocalTime.now();
  }

  public LocalDateTime getNow() {
    return LocalDateTime.now();
  }

  public void setChosenEmployee(Employee employee) throws IllegalArgumentException {
    if (!this.employees.contains(employee)) {
      throw new IllegalArgumentException(
          employee.toString() + " does not exist");
    }
    this.chosenEmployee = employee;
  }

  public Employee getChosenEmployee() {
    return this.chosenEmployee;
  }

  public ArrayList<Employee> getEmployees() {
    switch (state) {
      case TEST:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN TESTING STATE***");
        return new ArrayList<>(this.employees);

      // TODO: Add APIOFF state

      default:
        // In case the Application have been started and need to refresh
        if (this.employees.size() == 0) {
          try {
            this.readEmployees();
          } catch (IOException e) {
            System.out.println("Could not connect to the API");
            // TODO: Set in APIOFF state
            e.printStackTrace();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      return new ArrayList<>(this.employees);
    }

    
  }

  public void createEmployee(String name) throws IllegalArgumentException, IOException {
    // TODO: Validate name with Regex. Maybe split into first and sur name
    if (name.equals("")) {
      throw new IllegalArgumentException("Input required, please enter name");
    }
    Employee employee = new Employee(name);
    this.employees.add(employee);

    switch (state) {
      case TEST:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN TESTING STATE***");
        break;

      default:
        this.apiHandler.createEmployee(employee);
        this.readEmployees();
        break;
    }
  }

  public void readEmployees() throws IOException {
    this.employees = this.apiHandler.getEmployees();
  }

  public boolean employeeIsSet() {
    return !Objects.isNull(this.chosenEmployee);
  }

  // If the employee is clocked in the Workday will be finished with the specified
  // timestamp.
  // Returns true if the employee is at work after successfull execution.
  public boolean clockEmployeeInOut(LocalDateTime dateTimeInput) throws IllegalStateException, IOException {
    if (!this.employeeIsSet()) {
      throw new IllegalStateException("No employee is selected");
    }

    LocalDateTime dateTime = dateTimeInput;

    if (!this.getChosenEmployee().isAtWork()) {
      this.getChosenEmployee().checkIn(dateTime);
    } else {
      this.getChosenEmployee().checkOut(dateTime);
    }

    switch (state) {
      case TEST:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN TESTING STATE***");
        break;

      default:
        this.apiHandler.updateEmployee(this.getChosenEmployee());
        break;
    }

    return this.getChosenEmployee().isAtWork();
  }

  // If the employee is clocked in the Workday will be finished with the specified
  // timestamp.
  // Returns true if the employee is at work after successfull execution.
  public boolean autoClockEmployeeInOut() throws IllegalStateException, IOException {
    if (this.chosenEmployee == null) {
      throw new IllegalStateException("No employee is selected");
    }
    LocalDateTime dateTime = this.getNow();

    if (!this.getChosenEmployee().isAtWork()) {
      this.getChosenEmployee().checkIn(dateTime);
    } else {
      this.getChosenEmployee().checkOut(dateTime);
    }

    switch (state) {
      case TEST:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN TESTING STATE***");
        break;

      default:
        this.apiHandler.updateEmployee(this.getChosenEmployee());
        break;
    }

    return this.getChosenEmployee().isAtWork();
  }

  public ArrayList<Workday> getEmployeeWorkdayHistory() throws IllegalStateException {
    if (!this.employeeIsSet()) {
      throw new IllegalStateException("No employee is selected");
    }

    return this.getChosenEmployee().getWorkdays();
  }

  public void editWorkday(Workday workday, LocalDateTime timeIn, LocalDateTime timeOut)
      throws IllegalStateException, IllegalArgumentException, IOException {
    if (this.chosenEmployee == null) {
      throw new IllegalStateException("No employee is selected");
    }
    this.getChosenEmployee().editWorkday(workday, timeIn, timeOut);
    this.apiHandler.updateEmployee(this.getChosenEmployee());
  }

  public void deleteWorkdayFromEmployee(Workday workday)
      throws IllegalStateException, IllegalArgumentException, IOException {
    if (this.chosenEmployee == null) {
      throw new IllegalStateException("No employee is selected");
    }

    this.getChosenEmployee().deleteWorkday(workday);
    this.apiHandler.updateEmployee(this.getChosenEmployee());
  }

  /**
   * Deleting the chosen employee.
   * 
   * @throws IllegalStateException if no employee is set.
   */
  public void deleteChosenEmployee() throws IllegalStateException {
    if (!this.employeeIsSet()) {
      throw new IllegalStateException("No employee is chosen...");
    }

    try {
      System.out.println(chosenEmployee.getId());
      this.apiHandler.deleteEmployee(chosenEmployee);
      this.readEmployees();
      this.chosenEmployee = null;
    } catch (Exception e) {
      // TODO: Handle exeption if server is offline
      System.err.println("Server is probably offline");
      e.printStackTrace();
    }
  }

}
