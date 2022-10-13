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
We have made sure to have a pretty good test coverage of all our classes, and we have tried to test expected cases to most of our methods. To run the tests, navigate to the `time-master` directory and use ```mvn test```
It will run all tests, and show the result in the Terminal, in addition to supplying a report with testcoverage. This report can be found in: `coverage/target/site/jacoco-aggregate/index.html`, and can be opened in a browser. It is an aggregated report for all our classes.


### Architecture 

Time Master-App consists of multiple classes. 
The class named TimeMaster is the main class, which is responsible for most of the functionality.
The class TimeMasterController decides what TimeMaster is supposed to do, based on user input, in UI. It links Employees and Workdays together 
The class TimeMAsterJsonParser reads and writes user input to Json. This is the functionality that makes it possible to save user input. The file format for the Json file is described in [json.md](json.md).
<img src="/docs/img/class.png" width="250px"/>
<img src="/docs/img/sequence.png" width="250px"/>