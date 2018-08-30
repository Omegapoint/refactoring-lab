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

# Agenda (avsett för läraren)

## Vad betyder refactoring? 

* Funktionellt säkra förändringar av kod.
* Fångar insikter.

## Fowler-katan

### Genomgång (30 min)

(dema 10 min, disussion, dema 10 min, diskussion, dema 10 min, diskussion)

switch priceCode => extract method "int amountFor(Rental)", för hand
fel => double
gör om refactoring från meny
snygga upp parameter och "result"-var

data envy i amountFor
move method till Rental - manuell, klipp ur method-body
behåll delegerande metod i Customer
rename - charge()

(10 min)

i statement - "replace temp with query", dvs inline på "thisAmount"
  (behövs egentligen inte göras än)
  kan motiveras av att man vill bryta ut metod "statementDetailsForRental"

i Customer.statement - frequentRenterPoint
int thisFrequentRenterPoint
...
frequentRenterPoint += thisFrequentRenterPoint
 extract method på fyra rader om thisFrequentRenterPoint
move method till Rental

(10 min)

pilla isär loopen och pilla ut totalAmount-beräkningen till egen loop
extract method som "totalCharge"

pilla ut "totalFrequentRenterPoints" på samma sätt

Överkurs: Bygg om looparn till stream().mapToInt().sum(). (Inbyggd refaktorisering)

### Övning (1h)

Börja från början, kör på egen hand parvis.

### Sammanfattning

Skriv ner tre saker du skulle vilja komma ihåg

### Paus (15 min)

### Repetition: en sak du ville komma ihåg

## Genomgång (30 min)

_Movie med subklasser_

Skjut price/amount vidare från Rental till Movie
* inline av getMovie i Rental
* move method trots att den vill ha "tillbaka" Rental-referens
* I Movie.price(Rental) gör "introduce paramater" på "rental.getDaysRented()"

Skapa subklass ChildrenMovie
Kopiera hela price-metoden från Movie till ChildrenMovie
Visa att det är ChildrenMovie.price som körs genom att ändra gränsen för rabatt
Eller lägg till en exception för typen CHILDREN.
Rensa ChildrenMovie.price från de andra casen.
Rensa Movie.price från Children-case
(notera likheten med "pilla isär loop")
Kopiera nu-reducerade pricemetoden från Movie till ny klass NewReleaseMovie
Rensa
Kopiera väldigt-reducerade pricemetoden från Movie till ny klass RegularMovie
Rensa
Nu kan vi göra Movie abstrakt - vi bör inte skapa objekt av den.
Nu kan vi ta bort price-metodens implmentation (som ändå är bisarr, gratis) - abstract

Begränsning -> film kan inte byta kategori under sin livstid.
Kan illustreras genom att ta bort setPriceCategory.
getPriceCategory kan returnera konstant; constructor parameter försvinner. 

### Övning (1h)

Gör själva parvis.

## Alternativ till del 2

(Istället - Movie har PriceCategory, strategy-pattern)
(Övning - refactorera mot detta mål.)
Movie: extract dependency -> ChargeForPriceCategory
Flytta initiering till konstruktor; extrahera parameter; titta i testet!
Dela upp i olika implementationer.
Extract interface ChargeFor

## Sammanfattning 

En punkt från varje deltagare


