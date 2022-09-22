# Release 1
I Release-1 har vi:
- satt opp prosjektet med maven
- satt opp prosjektet til bruk med Java-versjon 17+
- satt opp mulighet for bruk av Gitpod
- laget 5 klasser som dannes basisfunksjonaliteten til applikasjonen
- laget et enkelt brukergrensesnitt
- laget et enkelt fillagringssystem med CSV-filer
- laget noen tester som hovedsaklig tester den mest avanserte logikken i klassene.


### Klasser
- Workday | Inneholder logikken rundt arbeidstid
- Employee | Inneholder logikken rundt ansatte, og holder styr på relaterte Workday-objekter
- TimeMasterFileHandler | Inneholder logikken for å skrive til og lese fra fil.
- App | Inneholder logikken for oppstart av programmet
- TimeMasterController | Inneholder logikken som kobler sammen brukerinput med programmet.

### Målet for release 1
Vi fokuserte på å lage grunnfunksjonaliteten til programmet for å ha et godt utgangspunkt å bygge videre på. Det gjør det lettere å fordele arbeidsoppgaver som ikke skaper merge-konflikter. Programmet består nå av et enkelt brukergrensesnitt der det er mulig å velge ansatt og registrere tidspunkt den ansatte har kommet på jobb. 

### Fillagring
Når appen starter leser den inn fra filene over ansatte og registrerte arbeidsdager. Foreløpig er de ansatte hardkodet i employees.csv fila. Hver gang et nytt registrert tidspunkt blir lagt til lagres det til fil.

### Testing
Vi har laget en test til hver klasse utenom kontrolleren. Det blir først når layout og design av brukergrensesnitt er ferdig at det blir relevant å teste koblingen mellom brukergrensesnittet og programmet.
Noen tester sjekker enkel funksjonalitet, men stort sett sjekker testene den avanserte funksjonaliteten, som betinger at at denkle funksjonaliteten funker som den skal. De viktigste testene vi har foreløøpig sjekker at det fungerer å lese og skrive til fil.
