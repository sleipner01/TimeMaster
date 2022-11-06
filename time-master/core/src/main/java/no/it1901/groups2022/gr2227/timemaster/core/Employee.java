package no.it1901.groups2022.gr2227.timemaster.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Employee is a class to store data about employees in the application.
 * It depends on Workday to store an employees working hours.
 * An Employee object encapsulates the following:
 *
 * <ul>
 * <li>The employees id
 * <li>The employees name
 * <li>The employees list of Workdays
 * <li>The employees status (at work or not)
 * </ul>
 *
 * <p>The id is generated with java.util.UUID
 *
 * <p>The name is supplied through the constructor as has to follow spesific rules.
 * //TODO: Insert regex rule
 * 
 * <p>An employees Workdays are stored in an ArrayList.
 * This makes it easy to access all listed workdays as the data
 * should be easily altered.
 *
 * <p>The Employee object stores an atWork field to store the state
 * across sessions. As long as the Employee object is stated at work,
 * the latest workday has to be closed before a new one can be created.
 *
 * @author Amalie Erdal Mansåker
 * @author Magnus Byrkjeland
 * @version %I%, %G%
 * @since 1.0
 */
public class Employee {
  
  private String id;
  private String name;
  private ArrayList<Workday> workdays = new ArrayList<>();
  
  /**
   * Used for JSON-parser.
   */
  public Employee() {}
  
  /**
   * Creates an Employee object.
   * An employee-id is automatically created using java.util.UUID.
   *
   * @param name Has to follow regex rule: //TODO: insert regex rule
   */

  public Employee(String name) {
    this.id = generateId();
    this.name = name;
  }
  
  // Only for filewriters/readers
  public Employee(String id, String name) {
    this.id = id;
    this.name = name;
  }
  
  private String generateId() { 
    return UUID.randomUUID().toString(); 
  }

  private void validateCheckInTimestamp(LocalDateTime input) throws IllegalArgumentException {

    for (int i = this.workdays.size() - 1; i >= 0; i--) {
      Workday tempWorkday = workdays.get(i);

      // If the last workday isn't checked out - Error
      if (i == workdays.size() && !tempWorkday.isTimedOut()) {
        throw new IllegalArgumentException(
            "Your last workday isn't clocked out yet...\n"
            + "Workday: " + tempWorkday.toString()
            );
      }

      // If the timestamp is newer than the last ended workday - true
      if (i == workdays.size() - 1 && tempWorkday.isTimedOut()) {
        if (input.isAfter(tempWorkday.getTimeOut())) {
          return;
        }
      }
      
      // If the timestamp is in the middle of another workday - error
      if (input.isAfter(tempWorkday.getTimeIn()) && input.isBefore(tempWorkday.getTimeOut())) {
        throw new IllegalArgumentException(
          "** Input was in between workday **\n"
          + "Check in: " + tempWorkday.getTimeIn().toString() + "\n"
          + "Check out: " + tempWorkday.getTimeOut().toString()
        );
      }

      // If input is before the first workday checkin - true
      if (i == 0 && tempWorkday.getTimeIn().isAfter(input)) {
        return;
      }

      // As long as we are not checking the last workday
      if (0 < i) {
        // If input is in between tempWorkday and the one before
        if (this.workdays.get(i - 1).getTimeOut().isBefore(input)
            &&
            input.isBefore(tempWorkday.getTimeIn())) {
          return;
        }
      }

    }
  }

  private void validateCheckOutTimestamp(LocalDateTime input, Workday workday) 
      throws IllegalArgumentException {

    if (input.isBefore(workday.getTimeIn())) {
      throw new IllegalArgumentException(
        "Input cannot be before the workday started!\n"
        + "Check in: " + workday.getTimeIn().toString() + "\n"
        + "Input: " + input.toString() 
      );
    }

    if (workday.equals(this.getLatestWorkday())) {
      return;
    }

    for (int i = this.workdays.size() - 1; i >= 0; i--) {

      Workday tempWorkday = this.workdays.get(i);

      // If we are at the latest workday which is not closed,
      // the new workday must be before the latest workday.
      if (tempWorkday.equals(this.getLatestWorkday()) 
          || 
          input.isAfter(tempWorkday.getTimeIn())) {
        if (!tempWorkday.isTimedOut()) {
          if (workday.getTimeIn().isAfter(tempWorkday.getTimeIn())) {
            throw new IllegalArgumentException(
              "** Input comes in conflict with the following workday **\n"
              + "Check in: " + tempWorkday.getTimeIn().toString() + "\n"
              + "Input: " + input.toString()
            );
          }
          continue;
        }
      }

      if (workday.getTimeIn().isAfter(tempWorkday.getTimeOut())) {
        return;
      }

      // If we are at the first workday and the checkout comes in conflict with the workday
      if (i == 0) {
        if (input.isAfter(tempWorkday.getTimeIn())) {
          throw new IllegalArgumentException(
            "** Input comes in conflict with the following workday **\n"
            + "Check in: " + tempWorkday.getTimeIn().toString() + "\n"
            + "Check out: " + tempWorkday.getTimeOut().toString() + "\n"
            + "Input: " + input.toString()
          );
        }
      }

      if (workday.getTimeIn().isAfter(tempWorkday.getTimeIn())) {
        continue;
      }

      if (input.isAfter(tempWorkday.getTimeIn())) {
        throw new IllegalArgumentException(
          "** Input comes in conflict with the following workday **\n"
          + "Check in: " + tempWorkday.getTimeIn().toString() + "\n"
          + "Check out: " + tempWorkday.getTimeOut().toString() + "\n"
          + "Input: " + input.toString() + "\n"
        );
      } else {
        return;
      }

    }

  }

  /**
   * Creates a new workday with the supplied parameters.
   * Will be stored in a list, accessable through <code>getWorkdays()</code>.
   *
   * <p>The Employee object atWork field will be set <code>true</code>.
   *
   * <p>If the timestamp is in the middle of another workday at the employee,
   * the timestamp isn't considered valid.
   *
   * @param dateTimeInput the timestamp of the started workday.
   * @throws IllegalStateException    if the Employee is at work.
   * @throws IllegalArgumentException if the supplied parameters
   *                                  comes in conflict with another saved
   *                                  workday.
   * @see java.time.LocalDate
   * @see java.time.LocalTime
   */
  public void checkIn(LocalDateTime dateTimeInput) 
      throws IllegalStateException, IllegalArgumentException {
    if (isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is already at work!");
    }

    // Validating in addWorkday
    this.addWorkday(new Workday(dateTimeInput));
    System.out.println(this.toString() + " checked in at: " + dateTimeInput);
  }
  
  /**
   * Ends the latest added workday with the supplied parameters.
   *
   * <p>The Employee object atWork field will be set <code>false</code>.
   *
   * <p>If the timestamp is in the middle of another workday at the employee,
   * the timestamp isn't considered valid. If another workday at the employee
   * lies in between check in and this timestamp, it isn't considered valid.
   *
   * @param dateTimeInput               the timestamp of the ending workday.
   * @throws IllegalArgumentException   if the timestamp isn't valid.
   * @throws IllegalStateException      if the Employee isn't at work.
   * @see java.time.LocalDate
   * @see java.time.LocalTime
   */
  public void checkOut(LocalDateTime dateTimeInput) 
      throws IllegalStateException, IllegalArgumentException {
    if (!isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is not at work!");
    }

    validateCheckOutTimestamp(dateTimeInput, this.getLatestWorkday());

    this.getLatestWorkday().setTimeOut(dateTimeInput);
    System.out.println(this.toString() + " checked out at: " + dateTimeInput);
  }
  
  /**
   * Id created with UUID.
   *
   * @return employee id as a string.
   * @see java.util.UUID
   */
  public String getId() { 
    return this.id; 
  }
  
  /**
   * The name is stored as a single field.
   * Any prefixes or suffixes are already included.
   *
   * @return <code>String</code> with the employee's name.
   */
  public String getName() { 
    return this.name; 
  }

  private Workday getLatestWorkday() {
    if (this.workdays.size() < 1) {
      return null;
    }
    return this.workdays.get(this.workdays.size() - 1);
  }
  
  /**
   * Important state for the employee which controls sertain methods.
   *
   * <p><code>false</code> enables {@link Employee#checkIn(LocalDate, LocalTime)}.
   *
   * <p><code>true</code> enables {@link Employee#checkOut(LocalTime)}.
   *
   * @return <code>true</code> with the employee is at work.
   *         Otherwise: <code>false</code>
   */
  public boolean isAtWork() { 
    if (this.workdays.size() < 1) { 
      return false;
    }
    return !this.getLatestWorkday().isTimedOut();
  }
  
  /**
   * Stores the workday in the Employee-object.
   *
   * @param workday @see Workday
   * @throws IllegalStateException If the Workday-object is already added
   */
  public void addWorkday(Workday workday) throws IllegalArgumentException {
    if (this.workdays.contains(workday)) { 
      throw new IllegalArgumentException("Workday is already added.");
    }
    if (!workday.isTimedOut() && 0 < this.workdays.size()) {
      if (!this.getLatestWorkday().isTimedOut()) {
        throw new IllegalArgumentException(
          "** Cannot add another open workday while another open workday is open **\n"
          + "Conflicting workday: " + this.getLatestWorkday().toString()
        );
      }
      if (this.getLatestWorkday().getTimeOut().isAfter(workday.getTimeIn())) {
        throw new IllegalArgumentException(
          "** An open workday cannot be set before an earlier workday. **\n"
          + "Conflicting workday: " + this.getLatestWorkday().toString()
        );
      }
    }

    validateCheckInTimestamp(workday.getTimeIn());
    if (workday.isTimedOut()) {
      validateCheckOutTimestamp(workday.getTimeOut(), workday);
    }

    this.workdays.add(workday);
    this.sortWorkdaysAscending();
  }

  private void hasWorkday(Workday workday) throws IllegalArgumentException {
    if (!this.workdays.contains(workday)) { 
      throw new IllegalArgumentException(
        "Workday: " + workday.toString()
        + " doesn't exist at " + this.toString()
      );
    }
  }

  /**
   * Edits the provided workday as long as it exists at the employee.
   * The new timestamps must not come in conflict with any other workday
   * at the employee.
   *
   * @param workday                     workday to be edited.
   * @param timeIn                      new timestamp for check-in.
   * @param timeOut                     new timestamp for check-out.
   * @throws IllegalArgumentException   If the editing workday isn't at the employee,
   *                                    or the timestamps aren't valid.
   * @see Employee#checkIn(LocalDateTime)
   * @see Employee#checkOut(LocalDateTime)                               
   */
  public void editWorkday(Workday workday, LocalDateTime timeIn, LocalDateTime timeOut)
      throws IllegalArgumentException {
    this.hasWorkday(workday);

    this.deleteWorkday(workday);
    Workday editedWorkday = new Workday(timeIn);
    editedWorkday.setTimeOut(timeOut);
    try {
      this.addWorkday(editedWorkday);
    } catch (IllegalArgumentException e) {
      this.addWorkday(workday);
      throw new IllegalArgumentException(e.getMessage());
    } catch (Exception e) {
      this.addWorkday(workday);
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Deletes the provided workday from the employee.
   *
   * @param workday                     workday to be deleted.
   * @throws IllegalArgumentException   if the workday doesn't exist at the employee.
   */
  public void deleteWorkday(Workday workday) throws IllegalArgumentException {
    this.hasWorkday(workday);

    this.workdays.remove(workday);
  }
  
  /**
   * The returned list is mutable. Any actions performed won't affect the list in the object.
   *
   * @return List of Workday-objects added in the Employee-object
   */
  public ArrayList<Workday> getWorkdays() {
    return new ArrayList<>(this.workdays);
  }
  
  /**
   * Uses the sorted Workdays-list to retrieve the latest workday.
   *
   * @return                        clock-in time represented as a string: Date Time
   * @throws IllegalStateExeption   if there are no workdays.
   */
  public String getLatestClockIn() {
    Workday latest = workdays.get(workdays.size() - 1);
    return latest.getTimeInAsFormattedString();
  }

  private void sortWorkdaysAscending() {
    this.workdays.sort((a, b) -> { 
      return a.getTimeIn().compareTo(b.getTimeIn()); 
    });
  }
  
  /**
   * Returns a string representation of the object.
   *
   * @return employee represented as: id,name
   */
  @Override
  public String toString() {
    return this.id + "," + this.getName();
  }
}