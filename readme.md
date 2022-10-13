# Time Master

Welcome to the Time Master repo. For detailed description about the application, please visit [Time Master Application](time-master/README.md).

<hr>

## The Project Structure

### Project folder
The project files lies in the `time-master`-directory. Navigate to it using ```cd time-master``` in the terminal. All of the main-logic, persistency and user-interface is in this folder.

### Documentaion
Documentation for the different releases can be found in the ```docs```-directory. 

<hr>

## Cloning the project
### Gitpod
[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2227/gr2227)
<br>
The project is set up for Gitpod.

1. Use the button above, or the main button in Gitlab to open an instance of the repo.
2. When the instance is ready, the terminal is automatically initialized in the ´´´time-master``-directory.
3. Maven dependencies should already have been installed.
4. Navigate to ```fxui```-directory with: `cd fxui`
5. Execute `mvn clean javafx:run` in the terminal.
### Developer installation

1. In the terminal, excute: `git clone https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2227/gr2227.git`
2. Open the folder as a mven project in your preffered IDE.
3. Navigate to the ```time-master```-directory with: `cd time-master`
4. Execute ```mvn clean install```
5. Navigate to ```fxui```-directory with: `cd fxui`
6. Run the application with: `mvn clean javafx:run`

<hr>

## Integrations

The application is built to support and run with `javafx:run`. See [developer installation](./readme.md#developer-installation) above for detailed description on how to run the application.

### Testing

The test can be run with `mvn test` from the `time-master`-directory. This will generate a report with testcoverage. The report is stored as an index.html file at [`time-master/coverage/target/site/jacoco-aggregate/index.html`](time-master/coverage/target/site/jacoco-aggregate/), and can be opened in the browser.

### Code Quality

After running the application and test, a report over the code quality can be made with `mvn site`. It can be found at [`time-master/coverage/target/site/checkstyle-aggregate.html`](time-master/coverage/target/site/).
A report over detected bugs is made as well. It can be found at each of the modules in `time-master/{module}/target/site/spotbugs.html`.


<!-- ## Git conventions

[Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/)

- [Overview of different commit types](https://github.com/commitizen/conventional-commit-types/blob/v3.0.0/index.json)
- [Rules for commit messages](https://github.com/conventional-changelog/commitlint/tree/master/%40commitlint/config-conventional) -->