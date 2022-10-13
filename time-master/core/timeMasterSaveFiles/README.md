# JSON Structure

Each employee stores it's own workdays. This gives us easy filehandler code, and we don't have to implement linked savefiles wich can cause complex problems.


### Mixins

Current methods ignored by TimeMasterJsonParser:

- Employee.java
  - getLatestCheckIn()