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
    try {
      this.readEmployees();
    } catch (IOException e) {
      System.err.println("Api is not responding...");
    }

    // Default state. Will be overwritten if needed.
    this.setApplicationInProductionState();
  }

  /**
   * For testing purposes. Turns off API calls from TimeMaster.
   */
  public void setApplicationInTestState() {
    this.state = State.TEST;
  }

  /**
   * For testing purposes.
   * To reset TimeMaster state to production if needed after testing.
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
    return new ArrayList<>(this.employees);
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
        this.readEmployees(); // Updates the list of employees after a new employee has been added.
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

}
