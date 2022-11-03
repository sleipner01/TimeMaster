package no.it1901.groups2022.gr2227.timemaster.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Mixin class for Employee.java.
 * Any special cases to handle while
 * saving to file via Jackson JSONparser.
 *
 * @author Magnus Byrkjeland
 * @version %I%, %G%
 * @since 1.0
 */
public abstract class EmployeeMixin {

  /**
   * There is no setter for this method,
   * and will make the file-reader crash if saved to local files.
   *
   * @return method to ignore when running Json parser.
   */
  @JsonIgnore
  abstract String getLatestClockIn();

  /**
   * There is no setter for this method,
   * and will make the file-reader crash if saved to local files.
   *
   * @return method to ignore when running Json parser.
   */
  @JsonIgnore
  abstract boolean isAtWork();
}
