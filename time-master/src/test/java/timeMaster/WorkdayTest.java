package timeMaster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class WorkdayTest {
    
    // TODO: create mock values
    LocalDate testDate;
    LocalTime testTimeIn;
    LocalTime testTimeOut;

    Workday testWorkday;

    @BeforeEach
    public void createWorkday() {
        testWorkday = new Workday(testDate, testTimeIn);
    }

    // TODO: finish test
    @Test
    @DisplayName("Test ")
    public void setTimeOutTest() {
        assertTrue(true);
    } 
    
}
