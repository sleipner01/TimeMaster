# Release 1

I Release-1 har vi:
    - laget brukergrensesnitt
    - laget et enkelt fillagringssystem med CSV-filer
    - laget noen tester

Vi har to klasser for objektene som brukes:
    - Employee
    - Workday

I klassen TimeMasterFileHandler er logikken for å skrive til og lese fra fil.

Vi fokuserte på å lage et enkelt brukergrensesnitt der det er mulig å velge ansatt og registrere tidspunkt den ansatte har kommet eller dratt fra jobb. 

Når appen starter leser den inn fra filene over ansatte og registrerte arbeidsdager. 
Hver gang et nytt registrert tidspunkt blir lagt til lagres det til fil.

Testene sjekker blant annet at det fungerer å lese og skrive til fil.
