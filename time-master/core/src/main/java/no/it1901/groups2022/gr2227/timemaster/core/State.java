package no.it1901.groups2022.gr2227.timemaster.core;

/**
 * Enum with alloweed states for the application.
 *
 * <code>PRODUCTION</code>  - All functionality enabled. 
 * <code>LOCAL</code>       - API turned off. 
 *                            Only internal lists and/or local files. 
 * <code>TEST</code>        - Limits functionality for testing purposes.
 *                            I.E: API calls will only be run in classes that 
 *                            solely rely on the API.
 *
 * @author Magnus Byrkjeland
 * @version %I%, %G%
 * @since 1.0
 */
enum State {
  PRODUCTION,
  LOCAL,
  TEST
}
