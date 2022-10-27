package no.it1901.groups2022.gr2227.timemaster.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class WorkdayMixin {
  @JsonIgnore abstract boolean isTimedOut(); 
}
