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

Denna kata består av 4 olika små delar och upplägget är sådant att:
 * Läraren gör uppgiften 
* Studenterna härmar
* Diskutera varför. Till varje del så finns en diskussionsfråga.

Se till att alla har hittat repot: https://github.com/omegapoint/refactor-lab
#### Del 1
(dema 10 min, disussion, dema 10 min, diskussion, dema 10 min, diskussion)

switch priceCode => extract method "int amountFor(Rental)", för hand.
"Råka" göra fel och orsaka ett avrundningsfel => Rätta genom att göra om variabeln till en double.
Gör om refaktorisering men från menyn.
Snygga upp parameter (namnet) och "result"-variabel.

**Disskusion:** Vad är skillnaden att använda sig av använda sig av inbyggda verktyg? Lättheten att göra fel även vid 
små refaktoriseringar.

#### Del 2

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

**Diskussion:** varför gjorde vi denna rekatorinseringen? 

#### Del 3

I statement - "replace temp with query", dvs inline på "thisAmount"
  (behövs egentligen inte göras än)
  kan motiveras av att man vill bryta ut metod "statementDetailsForRental"
```java
        // show figures for this rental
        result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(each.getThisAmount()) + "\n";
        totalAmount += each.getThisAmount();
```

Argument för:

* Minskar beroendet. Behöver inte vara beroende av this amount
* ska man spendera tid på mikroptimeringar?

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

returnera olika beroende på villkor istället för att summera ihop och returnera resultatet. Börja
med att bryta det till en if-else istället för ternary. Låt dem se om de kan förända det till ternary.

 extract method på fyra rader om thisFrequentRenterPoint
move method till Rental

```java
    int getFrequentRentalPoints() {
        return getMovie().getPriceCode() == Movie.NEW_RELEASE && getDaysRented() > 1 ? 2 : 1;
    }
```

**Diskussion:** Vad är skillnaden mellan enkelt/lätt gentemot att vara van vid. 

#### Del 4

(10 min)

Pilla isär loopen och pilla ut totalAmount-beräkningen till egen loop.

När man får en metod som tar in en totalAmount och skickar tillbaka en totalamount. för att undvika skapa en tmpvariabel 
tmpAmount för totalamount och sedan tilldela den efter loopen från värdet av tmpAmount. Då när du tar extract method så 
kommer du inte få att den tar in samma sak som den returnerar. 

Ska denna då flyttas till Rentals? Nej för den handhar flera olika rentals. 

Pilla ut "totalFrequentRenterPoints" på samma sätt som ovanstånde.

Överkurs: Bygg om looparna till stream().mapToInt().sum(). (Inbyggd refaktorisering).

Extrahera beskrivningsraderna och konvertera till stream. Det får de göra själva. Så elaka är vi. 

Nu kan man även göra replace tmpvariable with query instead. med andra ord kan vi ersätta totalAmount och Frequentpoints
med ett metodanrop. 

### Sammanfattning

Skriv ner tre saker du skulle vilja komma ihåg.

### Paus (15 min/1h 10 min)

### Repetition: en sak du ville komma ihåg

Kodlukter: Långa metoder, dataavundsjuka.
Refaktoriseringar: ??

### Genomgång (30 min/1h 40 min)

Mål: Isolera prisberäkningen.

Vi får reda på att butiken kommer att vilja ha fler typer av priser
och kunna byta prissättning på filmerna efterhand. Kan vi förändra
koden så att det blir lättare att stödja en sådan funktion?

Inuti varje switch-sats finns några små klasser som vill komma ut.
Open-Closed principle. Enklare att lägga till en ny priskategori utan att
modifiera i Rental eller Customer.

Skjut price/amount vidare från Rental till Movie.
* Inline av getMovie i Rental.
* Extrahera tillfällig amount-metod i rental.
* Flytta metoden trots att den vill ha "tillbaka" Rental-referens.
* I Movie.price(Rental) gör "introduce paramater" på "rental.getDaysRented()"

Med strategi:

Extrahera anropet av getPriceCode i amount till en lokal variabel
för att slippa parameter.
Extrahera en tillfällig metod: amountForCategory
Extrahera amountForCategory till delegat; PriceCategoryImpl.
Gör amountForCategory publik annars går den inte att flytta till interfacet!
Extrahera interface för PriceCategoryImpl; PriceCategory.
Glöm inte att ersätta med interface på alla ställen där det går.
Flytta initieringen till constructor.

Kopiera PriceCategoryImpl till ChildrensPriceCategory.
Ändra i switch så att bara CHILDRENS price category accepteras.
Ändra i parametern i konstruktorn i testet.

Kopiera PriceCategoryImpl till NewRelasePriceCategory.
Ändra i switch så att bara NEW_RELEASE price category accepteras.
Ändra i parametern i konstruktorn i testet.

Byt namn på PriceCategoryImpl till RegularPriceCategory.
Ändra i switch så att bara hanterar REGULAR.

Testfel! Diskussion.

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

Kodlukter: Switch-sats. Överkomplicerade beräkningar med mutationer.
Refaktoriseringar: ??

En punkt från varje deltagare.


### Extra material

- Thread-safe refactoring
- Reflection safe refactoring
