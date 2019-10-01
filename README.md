# Refaktorisering

## Beskrivning

Semnarium för dig som tittat i Refactoring-menyn och känner på dig att det finns
betydligt mer bortom "Rename". Genom konkreta demo och praktiska övningar
bekantar vi oss närmare med konkreta handgrepp och tar dessa som utgångpunkt
för diskussioner om pragmatik och filosofi.

Tid: 4h

## Instruktion (för deltagaren)

Labbkoden finns i form av ett maven-projekt definierat av pom.xml

För att öppna i IntelliJ: välj "Import Project" och markera biblioteket "refactoring-lab", importera som Maven-projekt

Kör enhetstestet i CustomerTest.

## Del 1: Dela upp och och organisera om koden i statement-metoden

Vi ärver en halvrörig kod. Vad kan vi göra för att göra koden enklare och lättare att
förstå?

- Extrahera metod för prisberäkning; amountFor
- ...

Kodlukter:

- Lång metod
- Stor klass
- Funktionsavundsjuka
- Tillfälliga variabler

## Del 2: Isolera prisberäkningen.

Vi får reda på att butiken kommer att vilja ha fler typer av priser
och kunna byta prissättning på filmerna efterhand. Kan vi förändra
koden så att det blir lättare att stödja en sådan funktion?

- Flytta metoden price/amount vidare från Rental till Movie.
- Dela upp switch-satsen i tre klasser och ett interface (strategy pattern).

Kodlukter:

- Switch-sats (https://refactoring.guru/smells/switch-statements)
- Onödig intimitet
- Tillfälliga variabler

## Referenser

- Martin Fowler: Refactoring: Improving the Design of Existing Code (2nd Edition)
- https://refactoring.com/catalog
- Joshua Kerievsky: Refactoring to Patterns 1st Edition
- https://refactoring.guru

