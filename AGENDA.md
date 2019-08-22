# Agenda (avsett för läraren)

## Förväntningar

Vad har du för förväntningar?
Vad betyder det för dig?

## Vad betyder refactoring? 

* Funktionellt säkra förändringar av kod.

## Fowler-katan

En lätt moderniserad version av av övnignen från Fowlers bok.
Vad är en kata?

"Kata är en rad förutbestämda rörelser, som utförs mot "osynliga" motståndare i luften.
Ordets betydelse innebär att i träningens rörelsemönster eftersträva perfektion.
Poängen med kata är att så grundligt lära in de tekniker som finns i katan,
att de sedan snabbt och korrekt kan användas i en uppkommen nödvärns-situation eller tävling."

Det är också ett släkte plattmaskar. Och ett vattendrag i Centralafrikanska republiken.

### Genomgång (30 min)

(dema 10 min, disussion, dema 10 min, diskussion, dema 10 min, diskussion)

switch priceCode => extract method "int amountFor(Rental)", för hand.
fel => double
Gör om refactoring från meny.
Snygga upp parameter (namnet) och "result"-variabel.

data envy i amountFor.
Flytta den nya metoden till Rental - manuell, klipp ur method-body.
behåll delegerande metod i Customer.
Byt namn till t.ex. charge().
```java
double charge() {
        double thisAmount = 0;
        // determine amount for each line
        switch (getMovie().getPriceCode()) {
            case Movie.REGULAR:
                thisAmount += 2;
                if (getDaysRented() > 2)
                    thisAmount += (getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                thisAmount += 1.5;
                if(getDaysRented() > 3)
                    thisAmount += (getDaysRented() - 3) * 1.5;
                break;
        }
        return thisAmount;
    }
```
(10 min)

I statement - "replace temp with query", dvs inline på "thisAmount"
  (behövs egentligen inte göras än)
  kan motiveras av att man vill bryta ut metod "statementDetailsForRental"
```java
        // show figures for this rental
        result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(each.getThisAmount()) + "\n";
        totalAmount += each.getThisAmount();
```

Byt ut ++ mot += för att göra algoritmen tydligare.
i Customer.statement - frequentRenterPoint
int thisFrequentRenterPoint
...
frequentRenterPoint += thisFrequentRenterPoint
```java
        // add frequent renter points
        int tmpPoints = 0;
        tmpPoints += 1;
        // add bonus for two days new release rental
        if((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1) {
            tmpPoints += 1;
        }
        frequentRenterPoints += tmpPoints;

```

 extract method på fyra rader om thisFrequentRenterPoint
move method till Rental
returnera olika beroende på villkor istället för att summera ihop och returnera resultatet
```java
    int getFrequentRentalPoints() {
        return getMovie().getPriceCode() == Movie.NEW_RELEASE && getDaysRented() > 1 ? 2 : 1;
    }
```

(10 min)

pilla isär loopen och pilla ut totalAmount-beräkningen till egen loop
extract method som "totalCharge"

pilla ut "totalFrequentRenterPoints" på samma sätt

Överkurs: Bygg om looparna till stream().mapToInt().sum(). (Inbyggd refaktorisering)

### Övning (1h)

Börja från början, kör på egen hand parvis.

### Sammanfattning

Skriv ner tre saker du skulle vilja komma ihåg

### Paus (15 min)

### Repetition: en sak du ville komma ihåg

### Genomgång (30 min)

Mål: Isolera prisberäkningen.

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

### Alternativ till del 2

(Istället - Movie har PriceCategory, strategy-pattern)
(Övning - refactorera mot detta mål.)
Movie: extract dependency -> ChargeForPriceCategory
Flytta initiering till konstruktor; extrahera parameter; titta i testet!
Dela upp i olika implementationer.
Extract interface ChargeFor

## Sammanfattning 

En punkt från varje deltagare

### Extra material

- Thread-safe refactoring
- Reflection safe refactoring
