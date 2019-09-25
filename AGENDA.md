# Agenda (avsett för läraren)

## Presentera er! (5 min)

## Förväntningar (10 min/15 min)

Vad har du för förväntningar?
Vad betyder det för dig?

## Vad betyder refaktorisering? (5 min/20 min)

* Funktionellt säkra förändringar av kod.

## Fowler-katan (5 min/25 min)

En lätt moderniserad version av av övningen från Fowlers bok.
Vad är en kata?

"Kata är en rad förutbestämda rörelser, som utförs mot "osynliga" motståndare i luften.
Ordets betydelse innebär att i träningens rörelsemönster eftersträva perfektion.
Poängen med kata är att så grundligt lära in de tekniker som finns i katan,
att de sedan snabbt och korrekt kan användas i en uppkommen nödvärns-situation eller tävling."

Det är också ett släkte plattmaskar. Och ett vattendrag i Centralafrikanska republiken.

### Genomgång (30 min/55 min)

(dema 10 min, disussion, dema 10 min, diskussion, dema 10 min, diskussion)

switch priceCode => extract method "int amountFor(Rental)", för hand.
"Råka" göra fel och orsaka ett avrundningsfel => Rätta genom att göra om variabeln till en double.
Gör om refaktorisering men från menyn.
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

Pilla isär loopen och pilla ut totalAmount-beräkningen till egen loop.
Bryt ut metoden som "totalCharge"

Pilla ut "totalFrequentRenterPoints" på samma sätt.

Överkurs: Bygg om looparna till stream().mapToInt().sum(). (Inbyggd refaktorisering).

Extrahera beskrivningsraderna och konvertera till stream.

### Övning (1h)

Börja från början, kör på egen hand parvis.

### Sammanfattning

Skriv ner tre saker du skulle vilja komma ihåg

### Paus (15 min/1h 10 min)

### Repetition: en sak du ville komma ihåg

### Genomgång (30 min/1h 40 min)

Mål: Isolera prisberäkningen.

Inuti varje switch-sats finns några små klasser som vill komma ut.

Skjut price/amount vidare från Rental till Movie.
* Inline av getMovie i Rental.
* Flytta metoden trots att den vill ha "tillbaka" Rental-referens.
* I Movie.price(Rental) gör "introduce paramater" på "rental.getDaysRented()"

Med strategi:

(Istället - Movie har PriceCategory, strategy-pattern)
(Övning - refactorera mot detta mål.)

Extrahera anropet av getPriceCode i amount till en lokal variabel.
Extrahera en tillfällig metod: amountForCategory
Extrahera amountForCateory till delegat; PriceCategoryImpl.
Gör amountForCategory publik annars går den inte att flytta till interfacet!
Extrahera interface för PriceCatregoyImpl; PriceCategory.
Glöm inte att ersätta med interface på alla ställen där det går.
Flytta initieringen till constructor.

Kopiera PriceCategoryImpl till ChildrensPriceCategory.
Ändra i switch så att bara CHILDRENS price category accepteras.
Ändra i parametern i konstruktorn i testet.

Kopiera PriceCategoryImpl till NewRelasePriceCategory.
Ändra i switch så att bara NEW_RELEASE price category accepteras.
Ändra i parametern i konstruktorn i testet.

Byt namn på PriceCategoryImpl till RegularPriceCategory.
Ändra i switch så att bara REGULAR.

Ta bort kontrollen av pricecode i alla price category implementationer.

Ta bort parametern pricecode.

Inför setPriceCategory i Movie?

Med arv:

Skapa subklass ChildrenMovie.
Kopiera hela price-metoden från Movie till ChildrenMovie.
Visa att det är ChildrenMovie.price som körs genom att ändra gränsen för rabatt.
Eller lägg till en exception för typen CHILDREN.
Rensa ChildrenMovie.price från de andra casen.
Rensa Movie.price från Children-case.
(notera likheten med "pilla isär loop")
Kopiera nu-reducerade pricemetoden från Movie till ny klass NewReleaseMovie.
Rensa.
Kopiera väldigt-reducerade pricemetoden från Movie till ny klass RegularMovie.
Rensa.
Nu kan vi göra Movie abstrakt - vi bör inte skapa objekt av den.
Nu kan vi ta bort price-metodens implmentation (som ändå är bisarr, gratis) - abstract.

Begränsning -> film kan inte byta kategori under sin livstid.
Kan illustreras genom att ta bort setPriceCategory.
getPriceCategory kan returnera konstant; constructor parameter försvinner. 

### Övning (60 min/2h 40 min)

Gör själva parvis.

## Sammanfattning (20 min/3h)

En punkt från varje deltagare

### Extra material

- Thread-safe refactoring
- Reflection safe refactoring
