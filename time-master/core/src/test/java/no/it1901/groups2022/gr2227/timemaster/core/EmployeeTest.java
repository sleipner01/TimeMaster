package no.it1901.groups2022.gr2227.timemaster.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
	
	String testName = "testName";
	Employee employee1;
	Employee employee2;
	LocalDateTime dateTime1;
	LocalDateTime dateTime2;
	Workday workday1;
	Workday workday2;
	
	@BeforeEach
	public void createEmployee() {
		employee1 = new Employee(testName);
		employee2 = new Employee(testName);
	}
	
	@BeforeEach
	public void createDateTimes() {
		dateTime1 = LocalDateTime.now();
		dateTime2 = LocalDateTime.now().plusHours(2);
	}

	@BeforeEach
	public void createWorkdays() {
		workday1 = new Workday(dateTime1);
		workday2 = new Workday(dateTime2);
	}
	
	@Test
	public void nameTest() {
		assertTrue(employee1.getName().equals(testName));
	} 
	
	@Test
	public void idTest() {
		assertNotEquals(employee1.getId(), employee2.getId());
	}
	
	@Test
	public void checkInTest() {
		employee1.checkIn(dateTime1);
		assertTrue(employee1.isAtWork());
	}
	
	@Test
	public void checkOutTest() {
		employee1.checkIn(dateTime1);
		employee1.checkOut(dateTime2);
		assertFalse(employee1.isAtWork());
	}
	
	@Test
	public void getTimeInTest() {
		employee1.checkIn(dateTime1);
		assertEquals(dateTime1.toString(), 
		employee1.getDate(dateTime1).getTimeIn().toString());
	}
	
	@Test
	public void getLatestClockInTest() {
		employee1.checkIn(dateTime1);
		assertEquals(dateTime1.toString(), employee1.getLatestClockIn());
	}
	
	@Test
	public void addWorkdaysTest() {
		employee1.addWorkday(workday1);
		assertTrue(employee1.getWorkdays().size() == 1);
		assertTrue(employee1.getWorkdays().get(0).equals(workday1));
		employee1.addWorkday(workday2);
		assertTrue(employee1.getWorkdays().get(1).equals(workday2));
	} 
	
	@Test
	public void addDuplicateWorkdayTest() {
		employee1.addWorkday(workday1);
		assertThrows(IllegalArgumentException.class, () -> { employee1.addWorkday(workday1); });
	}
	
}
