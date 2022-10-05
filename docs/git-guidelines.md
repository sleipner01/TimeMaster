# git guidelines

Retningslinjer for felles standard for commits, issues, branching og merge requests.

## commits

En commit skal:

- beskrive kort hva den inneholder
- kun beskrive innhold den relaterer til
- skrives på engelsk

En commit kan:

- beskrive hvorfor endringene er gjort
- referere til en issue eller merge request
- beskrive innholdet mer detaljert, men ikke hvordan det funker (dette gjøres i dokumentasjon)

<img src="/docs/bilder/commit.png" width="500px"/>

Eksempel på en god og en dårlig commit.


## issues

En issue skal:

- beskrive en arbeidsoppgave som skal utføres
- være relevant til prosjektet
- knyttes til en milepæl
- merkes med en tagg

## branching

Alt arbeid som utføres på prosjektet skal utføres på en branch som ikke er `master` branchen. Dette er for å sørge for at innholdet på `master` til en hver tid fungerer.

Arbeid som utføres på en branch skal være innen for samme område. Det vil si at man ikke skal arbeide med to urelaterte arbeidsoppgaver på samme branch.

## merge requests

Når arbeid på en branch er ferdig, skal dette flettes inn med `master`. Dette gjøres ved å sammenlikne branchen med `master` og rette opp eventuelle konflikter.

Når den er klar til å flettes skal det opprettes en merge request som skal bli sett over og godkjent av en annen på gruppen før den flettes inn i `master`.
