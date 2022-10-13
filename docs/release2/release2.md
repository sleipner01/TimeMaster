# Release 2
We have 
- divided the project into modules with dependencies between them
- set up file-persistence with Jackson
- set up Checkstyle to check the code quality
- set up Spotbugs to check for bugs in the code
- fixed the warnings from the Checkstyle-report
- set up JaCoCo to make an aggregated report for all modules
- made tests for all modules
- expanded the functionality of the UI
- made three diagrams

### Goal for release 2
Our goals for this release was to set up the different modules, get more comfortable with maven and the pom-files, and get used to working with each issue on a different branch. We also wanted to make the UI a bit more interesting if we found the time and everything else worked.  

### File-saving
The reading to and writing from file is done with JSON.
The app reads from the file containing employees and hours worked when it launches. Each time we clock in or out, it is saved to file.

### Code-quality
Checkstyle and Spotbugs checks the code-quality. These are made when running ```mvn site``` from the ```time-master``` directory. Checkstyle makes one report for the entire project, and the report can be found at `target/site/checkstyle-aggregate.html`. Spotbugs reports are made for the core and fxui module, and they can be found in
`core/target/site/spotbugs.html` and `fxui/target/site/spotbugs.html`.

### Testing
There is a test for each class, except the controller. The connection between the UI and the program won't be necessary to test before layout and design are finished.  
Some of the tests check the simple functionality, but our main focus has been advanced functionality, which depends on the simple functionality to work as intended. As of now, the most important test is the one that checks reading and writing to file. 

To run test, navigate to the ```time-master``` directory and use:
```mvn test```

It will run all tests, and show the result in the Terminal, in addition to supplying a report with testcoverage. Testcoverage can be fund in: `coverage/target/site/jacoco-aggregate/index.html`, and can be opened in a browser.
