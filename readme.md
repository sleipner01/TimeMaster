# Time Master

Velkommen til kodelageret til Time Master. For detaljert beskrivelse av applikasjonen, les [Time Master Application](time-master/README.md)

## Kodingsprosjektet

Kodingsprosjektet ligger i undermappen `time-master` som man kan navigere til med å bruke `cd time-master`. Der ligger også domenelogikken, persistens, og brukergrensesnittet til programmet.

Programmet er bygget med Maven slik at man enkelt kan kjøre programmet med `mvn javafx:run`.

Testene kan kjøres med `mvn test`. Da genereres en rapport over testdekningsgrad som legges i `target/site/jacoco/index.html`, og kan åpnes i en nettleser.

Etter kjøring av programmet og testene kan en rapport over kodekvaliteten lages med `mvn site`. Denne finnes i `target/site/checkstyle.html`. En rapport over bugs blir også laget samtidig, og kan finnes i `target/site/spotbugs.html`.


## Gitpod
[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2227/gr2227)

1. Åpne en Gitpod instanse av kodelageret. Terminalen blir automatisk satt til time-master mappen.
2. Kjør `mvn javafx:run` i terminalen, eventuelt `mvn clean javafx:run`.
## Utviklerinstallasjon

1. `git clone https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2227/gr2227.git`
2. åpne mappen som et Maven prosjekt i din IDE.
3. `cd time-master`
4. Installer modulene ved å kjøre `mvn install`
5. Kjør appen med `mvn javafx:run`

<!-- ## Git conventions

[Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/)

- [Overview of different commit types](https://github.com/commitizen/conventional-commit-types/blob/v3.0.0/index.json)
- [Rules for commit messages](https://github.com/conventional-changelog/commitlint/tree/master/%40commitlint/config-conventional) -->