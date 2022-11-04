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

    this.setApplicationInProductionState();      
  }

  public TimeMaster(boolean test) {
    this.employees = new ArrayList<Employee>();
    this.apiHandler = new ApiHandler();

    // Since it's neccessary to check test,
    // it's not possible to use this()
    if(test) {
      this.setApplicationInTestState();
    } else {
      this.setApplicationInProductionState();
    }
  }

  /**
   * For testing purposes. Turns off API calls from TimeMaster.
   * IMPORTANT: This methods clears internal lists.
   */
  public void setApplicationInTestState() {
    this.state = State.TEST;
    this.employees.clear();
    System.out.println("** Application set in testing state **");
  }

  /**
   * Setting application in production mode to enable API.
   * Also useful to reset TimeMaster state to production
   * if needed after testing.
   */
  public void setApplicationInProductionState() {
    if(this.getAPIStatus()) {
      this.state = State.PRODUCTION;
      System.out.println("** Application set in production state **");
    } else {
      System.out.println("** Could not connect to the API. Setting to local state. **");
      this.setApplicationInLocalState();
    }
  }

  /**
   * Disables API calls. Using internal lists. 
   * No data will be saved if the application is closed.
   */
  public void setApplicationInLocalState() {
    this.state = State.LOCAL;
    System.out.println("** Application set in local state **");
  }

  /**
   * Checking if the API is connected and returning a valid response.
   *
   * @return  <code>true</code> if API is connected and responding.
   *          <code>false</code> if API is disconnected or not responding.
   */
  public boolean getAPIStatus() {
    return this.apiHandler.checkServerStatus();
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
      case LOCAL:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN LOCAL STATE***");
        return new ArrayList<>(this.employees);
      case PRODUCTION:
        // In case the Application have been started and need to refresh
        if (this.employees.size() == 0) {
          try {
            this.readEmployees();
          } catch (IOException e) {
            System.out.println("Could not connect to the API");
            e.printStackTrace();
            this.setApplicationInLocalState();

          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        return new ArrayList<>(this.employees);
      default:
        System.out.println("***API CALL TURNED OFF. NO STATE SET. DEFAULT RETURN***");
        return new ArrayList<>(this.employees);
    }

    
  }

  public void createEmployee(String name) throws IllegalArgumentException, IOException {
    // TODO: Validate name with Regex. Maybe split into first and sur name
    if (name.equals("")) {
      throw new IllegalArgumentException("Input required, please enter name");
    }
    Employee employee = new Employee(name);

    switch (state) {
      case TEST:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN TESTING STATE***");
        this.employees.add(employee);
        break;

      case LOCAL:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN LOCAL STATE***");
        this.employees.add(employee);
        break;

      case PRODUCTION:
        // Adding locally in case something goes wrong within the API implementation.
        this.employees.add(employee);

        this.apiHandler.createEmployee(employee);
        this.readEmployees();
        break;
      default:
        System.out.println("***API CALL TURNED OFF. NO STATE SET. DEFAULT IMPLEMENTATION***");
        this.employees.add(employee);
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

      case LOCAL:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN LOCAL STATE***");
        break;

      case PRODUCTION:
        this.apiHandler.updateEmployee(this.getChosenEmployee());
        break;

      default:
        System.out.println("***API CALL TURNED OFF. NO STATE SET. DEFAULT RETURN***");

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

      case LOCAL:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN LOCAL STATE***");
        break;

      case PRODUCTION:
        this.apiHandler.updateEmployee(this.getChosenEmployee());
        break;

      default:
        System.out.println("***API CALL TURNED OFF. NO STATE SET. DEFAULT RETURN***");
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
