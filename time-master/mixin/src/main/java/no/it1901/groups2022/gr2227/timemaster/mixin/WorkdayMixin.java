package no.it1901.groups2022.gr2227.timemaster.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Mixin class for Workday.java.
 * Any special cases to handle while
 * saving to file via Jackson JSONparser.
 *
 * @author Magnus Byrkjeland
 * @version %I%, %G%
 * @since 1.0
 */
public abstract class WorkdayMixin {

  /**
   * There is no field for this method,
   * and will make the file-reader crash if saved to local files.
   *
   * @return method to ignore when running Json parser.
   */
  @JsonIgnore
  abstract boolean isTimedOut();

  /**
   * There is no setter or field for this method,
   * and will make the file-reader crash if saved to local files.
   *
   * @return method to ignore when running Json parser.
   */
  @JsonIgnore
  abstract String getTimeInAsFormattedString();

  /**
   * There is no setter or field for this method,
   * and will make the file-reader crash if saved to local files.
   *
   * @return method to ignore when running Json parser.
   */
  @JsonIgnore
  abstract String getTimeOutAsFormattedString();
}
