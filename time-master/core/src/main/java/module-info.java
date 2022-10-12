module timeMaster.core {

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires timeMaster.mixin;
    opens timeMaster.core;
    
    exports timeMaster.core;
}
