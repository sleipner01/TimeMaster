# Release 1
We have 
- set up the project with maven 
- set up the to be compatible with Java-version 17+
- enable the use of GitPod
- made 5 classes which supplies the most basic functionalities to the app
- made a simple user interface 
- enable file-saving with CSV-files
- made some test, which tests the most advanced logic in the classes

### Classes
- Workday | Logic that deals with working hours
- Employee | Logic that deals with employees, and relating Workday-objects
- TimeMasterFileHandler | Logic that deals with writing to and from file
- App | Logic that launches the program
- TimeMasterController | Logic which connects user input to the program

### Goal for release 1
Our focus was to make a foundation that we would be able to develop more later on. This made it easier to distribute tasks that won't cause merge conlifts. As of now the app consists of a simple user interface, where the user is able to choose an employee and register the time it arrived at work. 

### Fil-saving
The app reads from the file containing employees and hours worked when it launches. The employees are as of now hardcoded in employees.csv-file. Each time we register work time, it is saved to file.

### Testing
There is a test for each class, except the controller. The connection between the UI and the program won't be necessary to test before layout and design are finished.  
Some of the tests check the simple functionality, but our main focus has been advanced functionality, which depends on the simple functionality to work as intended. As of now, the most important test is the one that checks reading and writing to file. 

To run test, use:
```mvn test```

It will run all tests, and show the result in the Terminal, in addition to supplying a report with testcoverage. Testcoverage can be fund in: `target/site/jacoco/index.html`, and can be opened in a browser.
