package timeMaster;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

public class WorkdayTest {
    
    // TODO: create mock values
    LocalDate testDate;
    LocalTime testTimeIn;
    LocalTime testTimeOut;

    Workday testWorkday;

    @Before
    public void createWorkday() {
        testWorkday = new Workday(testDate, testTimeIn);
    }

    // TODO: finish test
    @Test
    public void setTimeOutTest() {
        assertTrue(true);
    } 
    
}
