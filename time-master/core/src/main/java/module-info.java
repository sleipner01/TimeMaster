module timeMaster.core {

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    opens timeMaster.core;
    
    exports timeMaster.core;
}
