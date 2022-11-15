/**
 * Persistence Module.
 *
 * @author Håvard Solberg Nybøe
 * @since 1.0
 * @version 1.0
 */
module no.it1901.groups2022.gr2227.timemaster.persistence {
  requires com.fasterxml.jackson.core;
  requires transitive com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.datatype.jsr310;

  exports no.it1901.groups2022.gr2227.timemaster.persistence;
}