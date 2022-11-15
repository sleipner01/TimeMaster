/**
 * Mixin Module. Defines methods that Jackson-JSON-parser should ignore in Core-files.
 *
 * @author Magnus Byrkjeland
 * @since 1.0
 * @version 1.0
 */
module no.it1901.groups2022.gr2227.timemaster.mixin {
    requires com.fasterxml.jackson.databind;
    
    opens no.it1901.groups2022.gr2227.timemaster.mixin;
    exports no.it1901.groups2022.gr2227.timemaster.mixin;
}
