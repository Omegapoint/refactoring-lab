# Refactoring kurs

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

- Extrahera metod för prisberäkning; amountFor
-
-

Kodlukter:

- Långa metoder
- Dataavundsjuka
- Tillfälliga variabler
- Sidoeffekter

## Del 2: Isolera prisberäkningen.

- Flytta metoden price/amount från Rental till Movie.
-
-

Kodlukter:

- Switch-sats (https://refactoring.guru/smells/switch-statements)

## Referenser

- Martin Fowler: Refactoring: Improving the Design of Existing Code 1st Edition
- Martin Fowler: Refactoring: Improving the Design of Existing Code (2nd Edition) (Släpps i december 2018)
- Joshua Kerievsky: Refactoring to Patterns 1st Edition
- https://refactoring.guru

