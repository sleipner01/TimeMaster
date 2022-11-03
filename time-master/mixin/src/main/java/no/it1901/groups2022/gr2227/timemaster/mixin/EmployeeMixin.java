package no.it1901.groups2022.gr2227.timemaster.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class EmployeeMixin {
  @JsonIgnore abstract String getLatestClockIn(); 
  @JsonIgnore abstract boolean isAtWork(); 
}

