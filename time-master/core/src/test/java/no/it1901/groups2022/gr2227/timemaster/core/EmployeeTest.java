package no.it1901.groups2022.gr2227.timemaster.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
	
	String testName = "Test Name";
	Employee employee1;
	Employee employee2;
	LocalDateTime dateTime1;
	LocalDateTime dateTime2;	
	LocalDateTime dateTime3;
	LocalDateTime dateTime4;
	LocalDateTime dateTime5;
	LocalDateTime dateTime6;	
	LocalDateTime dateTime7;
	LocalDateTime dateTime8;
	Workday workday1;
	Workday workday2;
	Workday workday3;
	Workday workday4;
	
	@BeforeEach
	public void createEmployee() {
		employee1 = new Employee(testName);
		employee2 = new Employee(testName);
	}
	
	@BeforeEach
	public void createDateTimes() {
		dateTime1 = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
		dateTime2 = LocalDateTime.of(1970, 1, 1, 2, 0, 0);
		dateTime3 = LocalDateTime.of(1970, 1, 1, 4, 0, 0);
		dateTime4 = LocalDateTime.of(1970, 1, 1, 6, 0, 0);
		dateTime5 = LocalDateTime.of(1970, 1, 1, 12, 0, 0);
		dateTime6 = LocalDateTime.of(1970, 1, 1, 14, 0, 0);
		dateTime7 = LocalDateTime.of(1970, 1, 1, 16, 0, 0);
		dateTime8 = LocalDateTime.of(1970, 1, 1, 18, 0, 0);
	}

	@BeforeEach
	public void createWorkdays() {
		workday1 = new Workday(dateTime1);
		workday2 = new Workday(dateTime3);
		workday3 = new Workday(dateTime5);
		workday4 = new Workday(dateTime7);
	}
	
	@Test
	public void nameTest() {
		assertTrue(employee1.getName().equals(testName));
	} 
	
	@Test
	public void getIdTest() {
		assertNotEquals(employee1.getId(), employee2.getId());
		assertDoesNotThrow(() -> UUID.fromString(employee1.getId()));
	}
	
	@Test
	public void checkInTest() {
		employee1.checkIn(dateTime2);
		assertThrows(IllegalStateException.class, () -> employee1.checkIn(dateTime2));
		employee1.checkOut(dateTime3);
		assertThrows(IllegalArgumentException.class, () -> employee1.checkIn(dateTime1));
	}
	
	@Test
	public void checkOutTest() {
		employee1.checkIn(dateTime2);
		assertThrows(IllegalArgumentException.class, () -> employee1.checkOut(dateTime1));
		employee1.checkOut(dateTime3);
		assertThrows(IllegalStateException.class, () -> employee1.checkOut(dateTime4));
	}

	@Test
	public void isAtWorkTest() {
		employee1.checkIn(dateTime1);
		assertTrue(employee1.isAtWork());
		employee1.checkOut(dateTime2);
		assertFalse(employee1.isAtWork());		
	}
	
	@Test
	public void getTimeInTest() {
		employee1.checkIn(dateTime1);
		assertEquals(dateTime1.toString(), employee1.getWorkdays().get(0).getTimeIn().toString());
		assertNotEquals(dateTime2.toString(), employee1.getWorkdays().get(0).getTimeIn().toString());
	}

	@Test
	public void getTimeOutTest() {
		employee1.checkIn(dateTime1);
		employee1.checkOut(dateTime2);
		assertEquals(dateTime2.toString(), employee1.getWorkdays().get(0).getTimeOut().toString());
		assertNotEquals(dateTime2.toString(), employee1.getWorkdays().get(0).getTimeIn().toString());
	}
	
	@Test
	public void getLatestClockInTest() {
		employee1.checkIn(dateTime1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E. MMM dd yyyy - HH:mm", Locale.getDefault());
		assertEquals(dateTime1.format(formatter), employee1.getLatestClockIn());
		employee1.checkOut(dateTime2);
		employee1.checkIn(dateTime3);
		assertNotEquals(dateTime1.format(formatter), employee1.getLatestClockIn());
		assertEquals(dateTime3.format(formatter), employee1.getLatestClockIn());
	}
	
	@Test
	public void addWorkdaysTest() {
		assertDoesNotThrow(() -> employee1.addWorkday(workday1));
		assertDoesNotThrow(() -> employee1.checkOut(dateTime2.plusHours(1)));
		assertTrue(employee1.getWorkdays().size() == 1);
		assertTrue(employee1.getWorkdays().get(0).equals(workday1));
		// Add duplicate
		assertThrows(IllegalArgumentException.class, () -> { employee1.addWorkday(workday1); });
		// Add another workday with later time in
		assertDoesNotThrow(() -> employee1.addWorkday(workday4));
		assertTrue(employee1.getWorkdays().get(1).equals(workday4));
		// Add a third workday to run through all validations
		workday3.setTimeOut(dateTime6);
		assertDoesNotThrow(() -> employee1.addWorkday(workday3));
		workday2.setTimeOut(dateTime4);
		assertDoesNotThrow(() -> employee1.addWorkday(workday2));
		// Try to add another open workday after an open workday, should fail
		assertThrows(IllegalArgumentException.class, () -> employee1.addWorkday(new Workday(workday4.getTimeIn().plusMinutes(2))));
		// Try to add another open workday before an open workday, should fail
		assertThrows(IllegalArgumentException.class, () -> employee1.addWorkday(new Workday(workday4.getTimeIn().minusMinutes(2))));
		// Add an workday with a timeout that conflicts with an open workday
		Workday tempWorkday = new Workday(workday4.getTimeIn().minusMinutes(2));
		tempWorkday.setTimeOut(workday4.getTimeIn().plusMinutes(2));
		assertThrows(IllegalArgumentException.class, () -> employee1.addWorkday(tempWorkday));
	} 

	@Test
	public void getWorkdaysTest() {
		workday1.setTimeOut(dateTime2);
		workday2.setTimeOut(dateTime4);
		employee1.addWorkday(workday2);
		employee1.addWorkday(workday1);
		List<Workday> workdays = employee1.getWorkdays();
		assertTrue(workdays.contains(workday1));
		assertTrue(workdays.contains(workday2));
		assertTrue(workdays.size() == 2);
		assertTrue(workdays.get(0).equals(workday1));
		assertTrue(workdays.get(1).equals(workday2));
	}

	@Test
	public void editWorkdayTest() {
		// Adding some workdays to make the validation run through all cases
		workday2.setTimeOut(dateTime4);
		employee1.addWorkday(workday2);
		workday3.setTimeOut(dateTime6);
		employee1.addWorkday(workday3);
		workday4.setTimeOut(dateTime8);
		employee1.addWorkday(workday4);
		assertTrue(employee1.getWorkdays().size() == 3);
		// Cannot edit workday that doesn't exist at the employee
		assertThrows(IllegalArgumentException.class, () -> employee1.editWorkday(workday1, LocalDateTime.MIN, LocalDateTime.MIN.plusHours(1)));
		// Cannot have time out before time in
		assertThrows(IllegalArgumentException.class, () -> employee1.editWorkday(workday1, LocalDateTime.MAX, LocalDateTime.MAX.minusHours(1)));
		// Adding the other workday
		workday1.setTimeOut(dateTime2);
		employee1.addWorkday(workday1);
		assertTrue(employee1.getWorkdays().size() == 4);
		// Cannot add a workday after an open workday
		employee1.checkIn(LocalDateTime.now());
		assertTrue(employee1.getWorkdays().size() == 5);
		assertThrows(IllegalArgumentException.class, () -> employee1.editWorkday(workday1, LocalDateTime.MAX.minusHours(1), LocalDateTime.MAX));
		assertTrue(employee1.getWorkdays().size() == 5);
		// Conflicting time out
		Workday tempWorkday = employee1.getWorkdays().get(0);
		assertThrows(IllegalArgumentException.class, () -> employee1.editWorkday(tempWorkday, tempWorkday.getTimeIn() ,workday2.getTimeIn().plusMinutes(2)));
		assertTrue(employee1.getWorkdays().size() == 5);
		// Conflicting time in
		Workday tempWorkday2 = employee1.getWorkdays().get(0);
		assertThrows(IllegalArgumentException.class, () -> employee1.editWorkday(tempWorkday2, workday2.getTimeOut().minusMinutes(2), workday2.getTimeOut().plusMinutes(2)));
		assertTrue(employee1.getWorkdays().size() == 5);
		// Overlapping
		Workday tempWorkday3 = employee1.getWorkdays().get(0);
		assertThrows(IllegalArgumentException.class, () -> employee1.editWorkday(tempWorkday3, workday2.getTimeIn().minusMinutes(2), workday2.getTimeOut().plusMinutes(2)));
		assertTrue(employee1.getWorkdays().size() == 5);
		// Add workday after closed workday - should work
		employee1.checkOut(LocalDateTime.now().plusMinutes(30));
		assertDoesNotThrow(() -> employee1.editWorkday(employee1.getWorkdays().get(0), LocalDateTime.MAX.minusHours(1), LocalDateTime.MAX));
		// 
	}

	@Test
	public void deleteWorkdayTest() {
		workday1.setTimeOut(dateTime2);
		workday2.setTimeOut(dateTime4);
		employee1.addWorkday(workday1);
		assertTrue(employee1.getWorkdays().size() == 1);
		assertThrows(IllegalArgumentException.class, () -> employee1.deleteWorkday(workday2));
		assertTrue(employee1.getWorkdays().size() == 1);
		employee1.addWorkday(workday2);
		assertTrue(employee1.getWorkdays().size() == 2);
		assertDoesNotThrow(() -> employee1.deleteWorkday(workday1));
		assertTrue(employee1.getWorkdays().size() == 1);
		assertEquals(employee1.getWorkdays().get(0), workday2);
	}

	@Test
	public void toStringTest() {
		String uuid = employee1.getId();
		String name = employee1.getName();
		assertEquals(uuid + "," + name, employee1.toString());
	}
	
}
