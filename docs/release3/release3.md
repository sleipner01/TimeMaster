# Release 3

## Progress since release 2

We have:

- Created a REST API with Jakatra running on a Jersey server
- Moved persistence from `core` and `TimeMasterJsonParser` to `FileHandler` in `rest`
- Created a data access layer in core called `ApiHandler` which handles all connections with the REST API
- Created tests for all new classes
- Improved teset coverage for existing classes
- Improved validation in GUI
  - (spesifiser mer)
- Created JavaDocs for all classes and modules
- Implemented workday history
  - (spesifiser mer)
$\vdots$

## Goals for release 3

- 

## User stories

[User stories](userhistory.md)

## REST API

[REST API endpoints](rest-api.md)

## App documentation

## Git guidelines

[Git guidelines](../git-guidelines.md)

## Test coverage
The application has 82% test coverage.
We have tried to test most methods and scenarios in all classes, and we have actively used the tests to make sure the program still worked when merging. Our CI/CD pipeline setup has contributed to this, as we can clearly see that the program still manages to build, setup the server, pass the tests and pass coverage. 

All members of the group have made tests in the different modules, and we have pair programmed alot when making the tests. We all agree that this greatly improved the quality of the tests, and how efficiency we were when making them. 

## Class diagram

## Package diagram

## Sequence Diagram
