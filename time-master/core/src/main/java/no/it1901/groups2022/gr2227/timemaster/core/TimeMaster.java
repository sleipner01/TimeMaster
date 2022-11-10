package no.it1901.groups2022.gr2227.timemaster.core;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Main class of the application.
 * Combines all the classes within the core module to create the application.
 *
 * <p>The aim of the class is to be able to run the whole application
 * from the methods within this class. 
 *
 * <p>This class performs differently based on what state it is in.
 * ({@link State})
 *
 * <p>TimeMaster is capable of connecting to an API.
 * ({@link ApiHandler})
 *
 * @author Amalie Erdal Mansåker
 * @author Magnus Byrkjeland
 * @author Håvard Solberg Nybøe
 * @author Karen Gjersem Bakke
 * @version %I%, %G%
 * @since 1.0
 * @see TimeMaster#setApplicationInProductionState()
 * @see TimeMaster#setApplicationInLocalState()
 * @see TimeMaster#setApplicationInTestState()
 * @see ApiHandler
 */
public class TimeMaster {

  private Employee chosenEmployee;
  private ArrayList<Employee> employees;
  private ApiHandler apiHandler;
  private State state;

  /**
   * Sets up a TimeMaster object.
   * Sets up an API-handler to check if a connection can be established.
   * If the API is running, the application will be set in the default state.
   * Else, the API calls will be disabled and the program will only run locally. 
   *
   * @see TimeMaster#setApplicationInProductionState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInTestState()
   */
  public TimeMaster() {
    this.employees = new ArrayList<Employee>();
    this.apiHandler = new ApiHandler();

    this.setApplicationInProductionState();      
  }

  /**
   * Constructor to be able to set up a TimeMaster object in testing state.
   *
   * @param test  if <code>true</code> testing state is initialized,
   *              else the applicaion will be tried started in production state.
   * @see TimeMaster#setApplicationInProductionState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInTestState()
   */
  public TimeMaster(boolean test) {
    this.employees = new ArrayList<Employee>();
    this.apiHandler = new ApiHandler();

    // Since it's neccessary to check test,
    // it's not possible to use this()
    if (test) {
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
   * Setting application in production mode. This will enable the API.
   * Also useful to reset TimeMaster state to production
   * if needed after testing.
   */
  public void setApplicationInProductionState() {
    if (this.getApiStatus()) {
      this.state = State.PRODUCTION;
      System.out.println("** Application set in production state **");
    } else {
      System.out.println("** Could not connect to the API. Setting to local state. **");
      this.setApplicationInLocalState();
    }
  }

  /**
   * Disables API calls. Using internal lists. 
   * The API may still be running and available. 
   * If API is wanted, see {@link TimeMaster#setApplicationInProductionState()}.
   *
   * <p>NOTE: No data will be saved if the application is closed.
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
  public boolean getApiStatus() {
    return this.apiHandler.checkServerStatus();
  }

  /**
   * Depending on the state of the application, it will use the API or not.
   *
   * @return <code>true</code> if the application is using the API,
   *         else <code>false</code>.
   * @see TimeMaster#setApplicationInTestState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInProductionState()
   */
  public boolean isUsingApi() {
    switch (state) {
      case TEST:
        return false;

      case LOCAL:
        return false;

      case PRODUCTION:
        return true;
      
      default:
        return false;
    }
  }

  /**
   * Uses
   * {@link 
   * <a href="
   * https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
   * ">
   * LocalDateTime#now()
   * </a>} to get the system clock timestamp.
   *
   * @return current timestamp
   */
  public LocalDateTime getNow() {
    return LocalDateTime.now();
  }

  /**
   * Sets an employee to perform methodcalls on.
   * The employee need to exist at the TimeMaster-object.
   *
   * @param employee                    Employee to be set.
   * @throws IllegalArgumentException   If it doesn't exist at this TimeMaster-object
   * @see Employee
   */
  public void setChosenEmployee(Employee employee) throws IllegalArgumentException {
    if (!this.employees.contains(employee)) {
      throw new IllegalArgumentException(
          employee.toString() + " does not exist");
    }
    this.chosenEmployee = employee;
  }

  /**
   * Returns the chosen employee in the TimeMaster-object.
   *
   * @return  the chosen employee,
   *          else <code>null</code>
   * @see Employee
   */
  public Employee getChosenEmployee() {
    return this.chosenEmployee;
  }

  /**
   * Returns a list of the employees stores in the TimeMaster-object.
   * Performs differently based on the state of the object.
   * <ul>
   * <li>Production
   * <ul>
   * <li>Performs an API call to retrive the latest data</li>
   * </ul>
   * </li>
   * <li>Local
   * <ul>
   * <li>Retrives data stored in the application</li>
   * </ul>
   * </li>
   * <li>Test
   * <ul>
   * <li>Retrives data stored in the application</li>
   * </ul>
   * </li>
   * </ul>
   *
   * @return  List of employees at the TimeMaster-object
   * @see Employee
   * @see State
   * @see TimeMaster#setApplicationInTestState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInProductionState()
   */
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

  /**
   * Creates an employee and stores it accordingly.
   * Performs differently based on the state of the object.
   * <ul>
   * <li>Production
   * <ul>
   * <li>Performs an API call to save the new Employee and load the new data</li>
   * </ul>
   * </li>
   * <li>Local
   * <ul>
   * <li>Creates the employee and stores it internally</li>
   * </ul>
   * </li>
   * <li>Test
   * <ul>
   * <li>Creates the employee and stores it internally</li>
   * </ul>
   * </li>
   * </ul>
   *
   * <p>The name of the employee has to follow spesific rules.
   * {@link Employee#Employee(String)}
   *
   * @throws  IOException               If the API-call fails.
   * @throws  IllegalArgumentExeption   If the parameter is invalid.
   * @see Employee
   * @see State
   * @see TimeMaster#setApplicationInTestState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInProductionState()
   */
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

  /**
   * Performs an API-call to get employee-data stored at the server. 
   *
   * @throws IOException  If API-call fails
   */
  public void readEmployees() throws IOException {
    this.employees = this.apiHandler.getEmployees();
  }

  /**
   * This method is to check if an employee is set.
   * If no employee is set many of the methods in the TimeMaster-object won't work.
   *
   * @return  <code>true</code> if an employee is set,
   *          else <code>false</code>
   * @see TimeMaster#setChosenEmployee(Employee)
   * @see TimeMaster#getChosenEmployee()
   */
  public boolean employeeIsSet() {
    return !Objects.isNull(this.chosenEmployee);
  }

  /**
   * Clocks an employee in or out with the provided timestamp.
   * If the employee isn't at work, the employee will check in.
   * If the employee is clocked in the active workday at the employee
   * will be finished with the specified timestamp.
   *
   * <p>Performs differently based on the state of the object.
   * <ul>
   * <li>Production
   * <ul>
   * <li>Performs an API call to update the employee data.</li>
   * </ul>
   * </li>
   * <li>Local
   * <ul>
   * <li>Stores the new data internally.</li>
   * </ul>
   * </li>
   * <li>Test
   * <ul>
   * <li>Stores the new data internally.</li>
   * </ul>
   * </li>
   * </ul>
   *
   * @param dateTimeInput           Timestamp of clock in or out.
   * @return                        {@link Employee#isAtWork()}
   * @throws IllegalStateException  If an employee isn't set.
   * @throws IllegalArgumentException  If the timestamp is invalid.
   * @throws IOException            If the API-call fails.
   * @see Employee
   * @see State
   * @see Employee#checkIn(LocalDateTime)
   * @see Employee#checkOut(LocalDateTime)
   * @see TimeMaster#setApplicationInTestState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInProductionState()
   */
  public boolean clockEmployeeInOut(LocalDateTime dateTimeInput) 
      throws IllegalStateException, IllegalArgumentException, IOException {
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

  /**
   * Automatically clocks an employee in or out using {@link TimeMaster#getNow()}.
   * If the employee isn't at work, the employee will check in.
   * If the employee is clocked in the active workday at the employee
   * will be finished with the specified timestamp.
   *
   * <p>Performs differently based on the state of the object.
   * <ul>
   * <li>Production
   * <ul>
   * <li>Performs an API call to update the employee data.</li>
   * </ul>
   * </li>
   * <li>Local
   * <ul>
   * <li>Stores the new data internally.</li>
   * </ul>
   * </li>
   * <li>Test
   * <ul>
   * <li>Stores the new data internally.</li>
   * </ul>
   * </li>
   * </ul>
   *
   * @return                        {@link Employee#isAtWork()}
   * @throws IllegalStateException  If an employee isn't set.
   * @throws IOException            If the API-call fails.
   * @see Employee
   * @see State
   * @see Employee#checkIn(LocalDateTime)
   * @see Employee#checkOut(LocalDateTime)
   * @see TimeMaster#setApplicationInTestState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInProductionState()
   */
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

  /**
   * Returns the chosen employees list of workdays.
   *
   * @return                        List of workdays.
   * @throws IllegalStateException  If no employee is set.
   */
  public ArrayList<Workday> getEmployeeWorkdayHistory() throws IllegalStateException {
    if (!this.employeeIsSet()) {
      throw new IllegalStateException("No employee is selected");
    }

    return this.getChosenEmployee().getWorkdays();
  }


  /**
   * Edits the provided workday of the chosen employee.
   * Conditional executions based on which state the application is set in.
   *
   * @param workday                     Original workday.
   * @param timeIn                      New timestamp for check in
   * @param timeOut                     New timestamp for check out
   * @throws IllegalStateException      If no employee is set
   * @throws IllegalArgumentException   If the workday does not exist at the employee.
   *                                    If the timestamps comes in conflict with some other
   *                                    workdays at the employee or is not valid.
   * @throws IOException                If the APU call fails.
   *
   * @see Employee#editWorkday(Workday, LocalDateTime, LocalDateTime)
   * @see TimeMaster#setChosenEmployee(Employee)
   * @see TimeMaster#getChosenEmployee(Employee)
   * @see TimeMaster#setApplicationInTestState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInProductionState()
   */
  public void editWorkday(Workday workday, LocalDateTime timeIn, LocalDateTime timeOut)
      throws IllegalStateException, IllegalArgumentException, IOException {
    if (this.chosenEmployee == null) {
      throw new IllegalStateException("No employee is selected");
    }

    this.getChosenEmployee().editWorkday(workday, timeIn, timeOut);

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
        this.apiHandler.updateEmployee(this.getChosenEmployee());
        break;
    }
  }

  /**
   * Deletes the provided workday from the chosen employee.
   * Conditional executions based on which state the application is set in.
   *
   * @param workday                     Workday to be deleted.
   * @throws IllegalStateException      If no employee is set.
   * @throws IllegalArgumentException   If the provided workday doesn't exist at the employee.
   * @throws IOException                If API call fails
   *
   * @see TimeMaster#setChosenEmployee(Employee)
   * @see TimeMaster#getChosenEmployee(Employee)
   * @see TimeMaster#setApplicationInTestState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInProductionState()
   */
  public void deleteWorkdayFromEmployee(Workday workday)
      throws IllegalStateException, IllegalArgumentException, IOException {
    if (this.chosenEmployee == null) {
      throw new IllegalStateException("No employee is selected");
    }

    switch (state) {
      case TEST:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN TESTING STATE***");
        this.getChosenEmployee().deleteWorkday(workday);
        break;
    
      case LOCAL:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN LOCAL STATE***");
        this.getChosenEmployee().deleteWorkday(workday);
        break;

      case PRODUCTION:
        this.getChosenEmployee().deleteWorkday(workday);
        this.apiHandler.updateEmployee(this.getChosenEmployee());
        break;

      default:
        System.out.println("***API CALL TURNED OFF. NO STATE SET. DEFAULT RETURN***");
        this.getChosenEmployee().deleteWorkday(workday);
        break;
    }


  }

  /**
   * Deleting the chosen employee.
   * Conditional executions based on which state the application is set in.
   *
   * @throws IllegalStateException  if no employee is set.
   * @throws IOException            if the API fails
   *
   * @see TimeMaster#setChosenEmployee(Employee)
   * @see TimeMaster#getChosenEmployee(Employee)
   * @see TimeMaster#setApplicationInTestState()
   * @see TimeMaster#setApplicationInLocalState()
   * @see TimeMaster#setApplicationInProductionState()
   */
  public void deleteChosenEmployee() throws IllegalStateException, IOException {
    if (!this.employeeIsSet()) {
      throw new IllegalStateException("No employee is chosen...");
    }

    switch (state) {

      case TEST:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN TESTING STATE***");
        this.employees.remove(chosenEmployee);
        break;

      case LOCAL:
        System.out.println("***API CALL TURNED OFF. APPLICATION IN LOCAL STATE***");
        this.employees.remove(chosenEmployee);
        break;

      case PRODUCTION:
        this.apiHandler.deleteEmployee(chosenEmployee);
        this.readEmployees();
        break;

      default:
        System.out.println("***API CALL TURNED OFF. NO STATE SET. DEFAULT RETURN***");
        this.employees.remove(chosenEmployee);
        break;
    }
  }

}
