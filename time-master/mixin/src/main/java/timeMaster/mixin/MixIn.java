package timeMaster.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class MixIn {
    @JsonIgnore abstract String getLatestClockIn(); 
}

