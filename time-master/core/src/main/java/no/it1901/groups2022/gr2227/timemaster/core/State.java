package no.it1901.groups2022.gr2227.timemaster.core;

/**
 * Enum with alloweed states for the application. *                            
 *
 * @author Magnus Byrkjeland
 * @version %I%, %G%
 * @since 1.0
 */
enum State {
  /**
   * Appliction set for production. All functionality enabled.
   */
  PRODUCTION,

  /**
   * Application set for local. Any API calls / HTTP requests disabled.
   * Only internal lists and/or local files. 
   */
  LOCAL,
  
  /**
   * - Limits functionality for testing purposes.
   */
  TEST
}
