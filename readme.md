# Time Master

Welcome to the Time Master repo. For detailed description about the application, please visit [Time Master Application](time-master/README.md).

## The Project Structure

### Project folder

The project files lies in the `time-master`-directory. Navigate to it using ```cd time-master``` in the terminal. All of the main-logic, persistency and user-interface is in this folder.

### Documentation

Documentation for the different releases can be found in the `docs`-directory, or by following the links below:

- [Release 1](docs/release1/release1.md)
- [Release 2](docs/release2/release2.md)
- [Release 3](docs/release3/release3.md)

## Cloning the project

### Gitpod

[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2227/gr2227)

The project is set up for Gitpod.

1. Use the button above, or the main button in Gitlab to open an instance of the repo.
2. When the instance is ready, the terminal is automatically initialized in the ´´´time-master``-directory.
3. Maven dependencies should already have been installed.
4. Navigate to `fxui`-directory with: `cd fxui`
5. Execute `mvn clean javafx:run` in the terminal.

### Developer installation

1. In the terminal, execute: `git clone https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2227/gr2227.git`
2. Open the folder as a maven project in your preferred IDE.
3. Navigate to the `rest`-directory with: `cd time-master/rest`
4. Execute `mvn exec:jav` to run a local server.
5. Open a new terminal.
6. Navigate to the `time-master`-directory with: `cd time-master`
7. Execute `mvn clean install`
8. Navigate to `fxui`-directory with: `cd fxui`
9. Run the application with: `mvn javafx:run`

## Integrations

The application is built to support and run with `javafx:run`. See [developer installation](./readme.md#developer-installation) above for detailed description on how to run the application.

### Testing

The test can be run with `mvn test` from the `time-master`-directory. This will generate a report with test coverage. The report is stored as an index.html file at [`time-master/coverage/target/site/jacoco-aggregate/index.html`](time-master/coverage/target/site/jacoco-aggregate/), and can be opened in the browser.

NOTE: The server must run for this to work. Follow instructions 3 and 4 in [developer installation](./readme.md#developer-installation) to start the server.

### Code Quality

After running the application and test, a report over the code quality can be made with `mvn site`. It can be found at [`time-master/coverage/target/site/checkstyle-aggregate.html`](time-master/coverage/target/site/checkstyle-aggregate.html).
A report over detected bugs is made as well. It can be found at each of the modules in `time-master/{module}/target/site/spotbugs.html`.

### Javadocs

HTML pages with documentation for every public or protected class/method can be generated with `mvn site`. Main HTML-file can be found at [`time-master/coverage/target/site/javadocs/index.html`](time-master/coverage/target/site/javadocs/index.html).
To generate javadoc for all methods in a module (including private methods), start a terminal in the respective module and execute `mvn javadoc:javadoc`.



### Packaging

The project can be packaged to make distribution easier. The following guide will generate both a compressed folder, and a system-spesific executable file.

#### Jar
1. Navigate to ```fxui```-directory with: `cd fxui`
2. Execute ```mvn package```

The .jar file can be found at `time-master/fxui/target/`.
#### Windows
1. Make sure to have .NET 3.5 installed. ([Microsoft download here...](https://www.microsoft.com/nb-no/download/details.aspx?id=21))
2. Make sure to have WiX Toolset v3 installed. ([Github download here...](https://github.com/wixtoolset/wix3/releases/tag/wix3112rtm))
3. Navigate to ```fxui```-directory with: `cd fxui`
4. Execute ```mvn clean compile javafx:jlink jpackage:japackage```

The .zip file can be found at `time-master/fxui/target/`
The .exe file can be found at `time-master/fxui/target/dist/`.
#### MacOS
1. Navigate to ```fxui```-directory with: `cd fxui`
2. Execute ```mvn clean compile javafx:jlink jpackage:japackage```

The .zip file can be found at `time-master/fxui/target/`
The .dmg file can be found at `time-master/fxui/target/dist/`.
#### Linux
To be explained...


<!-- ## Git conventions

[Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/)

- [Overview of different commit types](https://github.com/commitizen/conventional-commit-types/blob/v3.0.0/index.json)
- [Rules for commit messages](https://github.com/conventional-changelog/commitlint/tree/master/%40commitlint/config-conventional) -->