# Refaktorisering

## Beskrivning

Seminarium för dig som tittat i Refactoring-menyn och känner på dig att det finns
betydligt mer bortom "Rename". Genom konkreta demo och praktiska övningar
bekantar vi oss närmare med konkreta handgrepp och tar dessa som utgångspunkt
för diskussioner om pragmatik och filosofi.

Tid: 4h

## Instruktion (för deltagaren)

Labbkoden finns i form av ett maven-projekt definierat av pom.xml

För att öppna i IntelliJ: välj "Import Project" och markera biblioteket "refactoring-lab", importera som Maven-projekt

Kör enhetstestet i CustomerTest.

## Kata 1: Dela upp och organisera om koden i statement-metoden

Vi ärver en halvrörig kod. Vad kan vi göra för att göra koden enklare och lättare att
förstå?

Som huvudmål i denna del vill vi extrahera en metod för prisberäkning: charge().

Kodlukter:
- Lång metod
- Stor klass
- Funktionsavundsjuka
- Tillfälliga variabler

### Del 1.A
- Bryt ut `switch`-satsen ur loopen till en egen metod. Testa skillnaden med
  att göra det själv och att låta ditt IDE göra det åt dig.

### Del 1.B
- Flytta den nya metoden till Rental

### Del 1.C
- Gör inline på anropet till den nya metoden i `Customer::statement`.
- Byt ++ mot +=
- Summera till en temp-variabel och lägg till tempvariabeln till summan
- Bryt ut varje beräkning till en egen metod.
- Flytta varje ny metod till Rental

### Del 1.D
- Pilla isär loopen till en loop per beräkning.
- Bryt ut varje loop till en egen metod.
- EXTRA: Bygg om looparna till att använda streams.
- Ersätt temp-variabler med query.

## Kata 2: Isolera prisberäkningen.

Vi får reda på att butiken kommer att vilja ha fler typer av priser
och kunna byta prissättning på filmerna efterhand. Kan vi förändra
koden så att det blir lättare att stödja en sådan funktion?

### Del 2.A
- Flytta metoden price/amount vidare från Rental till Movie.
- Vi behöver bara rentaldays från rental.

### Del 2.B
- Extrahera getPriceCode till lokal variabel
- Extrahera _tillfällig_ metod priceForCategory(int daysRented, int priceCode)
- Extrahera metoden till delegate PriceCalculatorImpl

### Del 2.C
- Flytta initiering till konstruktor-parameter. Hur ser testet ut nu?
- Skapa interface PriceCalculator
- Kopiera PriceCalculatorImpl och döp till RegularPriceCalculator
- Ta bort delar som ej har med REGULAR_PRICE_CODE ur switch

### Del 2.D
- Byt ut till RegularPriceCalculator i test-konstruktor
- Göt motsvarande för Children och New Release som gjordes för Regular
- Kör testet

### Del 2.E
- Lägg till metod för att ändra calculator
- Ta bort switch statement
- Safe delete på priceCode

Kodlukter:

- Switch-sats (https://refactoring.guru/smells/switch-statements)
- Onödig intimitet
- Tillfälliga variabler

## Referenser

- Martin Fowler: Refactoring: Improving the Design of Existing Code (2nd Edition)
- https://refactoring.com/catalog
- Joshua Kerievsky: Refactoring to Patterns 1st Edition
- https://refactoring.guru

