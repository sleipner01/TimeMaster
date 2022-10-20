module it1901.groups2022.gr2227.timemaster.core {

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires it1901.groups2022.gr2227.timemaster.mixin;
    opens it1901.groups2022.gr2227.timemaster.core;
    
    exports it1901.groups2022.gr2227.timemaster.core;
}
