package no.it1901.groups2022.gr2227.timemaster.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * This class defines a workday. Two values are stored.
 * <ul>
 * <li>Datetime for check in.</li>
 * <li>Datetime for check out.</li>
 * </ul>
 * The fields uses {@link java.time.LocalDateTime}
 *
 * <p>The workday has to be created with a timestamp using 
 * {@link Workday#Workday(LocalDateTime)}.
 * While a workday does not have a timestamp for check out,
 * it will be considered active, I.E not timed out 
 * ({@link Workday#isTimedOut()}).
 *
 * @author Amalie Erdal Mansåker
 * @author Magnus Byrkjeland
 * @version %I%, %G%
 * @since 1.0
 */
public class Workday {
  
  private LocalDateTime timeIn;
  private LocalDateTime timeOut;
  
  /**
   * Constructor for JSON-parser.
   *
   * @see TimeMasterJsonParser
   */
  public Workday() {}

  /**
   * Creates a Workday-object.
   * The object will be considered active until a time-out timestamp
   * is set. 
   *
   * @param timeIn  timestamp for time in.
   * @see Workday#isTimedOut()
   */
  public Workday(LocalDateTime timeIn) {
    this.timeIn = timeIn;
  }

  private String formatDateTime(LocalDateTime dateTime) {
    // Using Locale.getDefault to create a predictable result.
    // Different regions uses different standards.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
        "E. MMM dd yyyy - HH:mm", Locale.getDefault()
    );
    return dateTime.format(formatter);
  }

  /**
   * Returning time-in object stored in this object.
   *
   * @return  time-in timestamp for this object.
   * @see java.time.LocalDateTime
   */
  public LocalDateTime getTimeIn() {
    return timeIn;
  }

  /**
   * Format: E. MMM dd yyyy - HH:mm
   * Locale: default locale of this instance of Java Virtual Machine
   *
   * <p>Example: <code>Thu. Jan 01 1970 - 00:00</code>
   *
   * @return formatted string.
   * @see java.time.format.DateTimeFormatter
   * @see java.util.Locale
   */
  public String getTimeInAsFormattedString() {
    return formatDateTime(this.timeIn);
  }

  /**
   * Returning time-out object stored in this object.
   *
   * @return  time-out timestamp for this object if set,
   *          else <code>null</code>
   * @see java.time.LocalDateTime
   */
  public LocalDateTime getTimeOut() {
    return timeOut;
  }

  /**
   * Format: E. MMM dd yyyy - HH:mm
   * Locale: default locale of this instance of Java Virtual Machine
   *
   * <p>Example: <code>Thu. Jan 01 1970 - 00:00</code>
   *
   * @return formatted string.
   * @see java.time.format.DateTimeFormatter
   * @see java.util.Locale
   */
  public String getTimeOutAsFormattedString() throws IllegalStateException {
    if (!this.isTimedOut()) {
      throw new IllegalStateException("The workday isn't timed out.");
    }
    return formatDateTime(this.timeOut);
  }

  /**
   * While a workday does not have a timestamp for check out,
   * it will be considered active, I.E not timed out.
   *
   * @return  <code>true</code> if time-out timestamp is set,
   *          else <code>false</code>
   */
  public boolean isTimedOut() {
    return this.timeOut != null;
  }

  private boolean isValidInput(LocalDateTime input) {
    // Checking for null to satify JSONParser setTimeOut()
    if (input == null) {
      return true;
    }
    if (input.isBefore(this.timeIn)) {
      return false;
    }

    return true;
  }

  /**
   * Setting the time-out timestamp with the provided paramenter.
   * The time-out timestamp must be after the time-in timestamp.
   *
   * @param timeOut                    timestamp for time-out.
   *                                   Can be <code>null</code> but won't affect the object.
   * @throws IllegalArgumentException  If the argument isn't valid.
   * @see java.time.LocalDateTime#isAfter(java.time.chrono.ChronoLocalDateTime)
   * @see java.time.LocalDateTime#isBefore(java.time.chrono.ChronoLocalDateTime)
   * @see java.time.LocalDateTime#isEqual(java.time.chrono.ChronoLocalDateTime)
   */
  public void setTimeOut(LocalDateTime timeOut) throws IllegalArgumentException {
    if (!isValidInput(timeOut)) {
      throw new IllegalArgumentException(
          "The timestamp is before time-in timestamp.\n"
              + "Time in: " + this.timeIn.toString() + "\n"
              + "Input: " + timeOut.toString());
    }
    this.timeOut = timeOut;
  }

  /**
   * String is formatted as:
   *
   * <p>Date | Time in | Time out
   *
   * <ul>
   * <li>Date
   * <ul>
   * <li>Example: <code>Thursday 1 January 1970</code></li>
   * </ul>
   * <li>Time in
   * <ul>
   * <li>Example: <code>00:00</code></li>
   * </ul>
   * <li>Time out
   * <ul>
   * <li>Example: <code>01:00</code></li>
   * </ul>
   * </ul>
   *
   * <p>If no time out timestamp is set, the position will be empty.
   *
   * @return formatted timestamp represented as a string
   */
  @Override
  public String toString() {
    LocalDateTime timeIn = this.getTimeIn();

    // Date
    String dayOfWeek = timeIn.getDayOfWeek().toString();
    String dayOfMonth = String.valueOf(timeIn.getDayOfMonth());
    String month = timeIn.getMonth().toString();
    String year = String.valueOf(timeIn.getYear());

    String date = dayOfWeek + " " + dayOfMonth + " " + month + " " + year;

    // Time
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault());
    String stampIn = timeIn.format(timeFormat);

    String stampOut = "";

    if (timeOut != null) {
      stampOut = this.getTimeOut().format(timeFormat);
    }

    return String.format("%-26.26s%10s%14s%10s%14s", date, "|", stampIn, "|", stampOut);
  }

}
