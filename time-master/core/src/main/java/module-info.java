module timeMaster.core {

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    opens timeMaster.core;
    
    exports timeMaster.core;
}
